<?php
class Stack {
	private $size;
  private $stack = array();
  private $was_set;

  public function __construct() {
    $this->size = 0;
    $this->was_set = false;
  }

  public function was_set() {
    return $this->was_set;
  }

  public function is_empty() {
    if($this->size == 0) {
      return true;
    } else {
      return false;
    }
  }

  public function push($data) {
    $this->stack[++$this->size] = $data;
    $this->was_set = true;
  }

  public function pop() {
    if(!$this->is_empty()) {
      $this->size--;
    }
  }

  public function top() {
    if(!$this->is_empty()) {
      return $this->stack[$this->size - 1];
    } else {
      return -1;
    }
  }

  public function recover() {
    $this->was_set = false;
  }
}



function getBrowser() { 
  $u_agent = $_SERVER['HTTP_USER_AGENT'];
  $bname = 'Unknown';

  // Get the name of the useragent yes seperately and for good reason
  if(preg_match('/MSIE/i',$u_agent) && !preg_match('/Opera/i',$u_agent)){
    $bname = 'Internet Explorer';
    $ub = "MSIE";
  }elseif(preg_match('/Firefox/i',$u_agent)){
    $bname = 'Mozilla Firefox';
    $ub = "Firefox";
  }elseif(preg_match('/OPR/i',$u_agent)){
    $bname = 'Opera';
    $ub = "Opera";
  }elseif(preg_match('/Chrome/i',$u_agent) && !preg_match('/Edge/i',$u_agent)){
    $bname = 'Google Chrome';
    $ub = "Chrome";
  }elseif(preg_match('/Safari/i',$u_agent) && !preg_match('/Edge/i',$u_agent)){
    $bname = 'Apple Safari';
    $ub = "Safari";
  }elseif(preg_match('/Netscape/i',$u_agent)){
    $bname = 'Netscape';
    $ub = "Netscape";
  }elseif(preg_match('/Edge/i',$u_agent)){
    $bname = 'Edge';
    $ub = "Edge";
  }elseif(preg_match('/Trident/i',$u_agent)){
    $bname = 'Internet Explorer';
    $ub = "MSIE";
  }

  return array(
    'userAgent' => $u_agent,
    'name'      => $bname,
  );
} 

function _show_element($controller) {

  //TODO make this a parameter-by-url always
  $ua = getBrowser();
  $brower_name = $ua['name'];
  if(($element_name = $controller->get_param('element_name')) ||
      ($element_name = $controller->get_param())){

    $view = $controller->get_view();
    if($element = $controller->get_element($element_name)){
      $view->set('name', $element['name']);
      $view->set('element_kind', $element['element_kind']);

      //Create table with parameters needed for constructor
      //TODO do not create html here, leave it to the view
      if(count($element['constructor_params']) > 0){
        $constructor_table_body = '<tr><th>Attribute Name</th><th>Type</th><th>Value</th><th></th></tr>';
        $form_enabled = true;
        $input_field_index = 0;
        foreach($element['constructor_params'] as $p){
          $field = array();
          if(isset($element['attributes'][$p])){
            $field = $element['attributes'][$p];
            $value = isset($field['value']) ? $att['value'] : ''; 
            if($field['type'] == 'Boolean' || $field['type'] == 'boolean' || $field['type'] == 'bool') {
              $input_field = "<input type='radio' name='constructor_values[$input_field_index]' value='true'/> true ".
              "<input type='radio' name='constructor_values[$input_field_index]' value='false'/> false ".
              "<input type='radio' name='constructor_values[$input_field_index]' style='display:none;' value='unassigned' checked='checked'/>";
              $input_field_index++;
            } else if($field['type'] == 'Date' && $brower_name != 'Internet Explorer' && $brower_name != 'Apple Safari') {
              $input_field = "<input type='date' name='constructor_values[$input_field_index]' required pattern='[0-9]{4}-[0-9]{2}-[0-9]{2}'/>";
              $input_field_index++;
            } else if($field['type'] == 'Time' && $brower_name != 'Internet Explorer' && $brower_name != 'Apple Safari') {
              $input_field = "<input type='time' name='constructor_values[$input_field_index]' required step='1'/>";
              $input_field_index++;
            } else {
              $input_field = "<input type='text' name='constructor_values[$input_field_index]' value='$value'/>";
              $input_field_index++;
            }
          }else{
            $field = $element['associations'][$p];
            //Parameter for an association must be an index ("ID") for an existing instance
            $instances = $controller->get_objects($field['type']); 
            if(!empty($instances) && count($instances) > 0){
              $input_field = "<select name='constructor_values[$input_field_index]'>";
              $input_field_index++;
              for($i=0; $i < count($instances); $i++){
                $input_field .=  "<option value='{$i}'>Object ID {$i}</option>";
              }
              $input_field .= '</select>';

            }else {
              $input_field = "<input type='text' disabled='disabled'/>";
              $form_enabled = false;
            }
          }

          $attribute_type = $field['type'];
          $hidden_type = "<input type='hidden' name='attribute_types[]' value='$attribute_type'/>";

          $constructor_table_body .= "<tr><td>{$field['name']}</td>".
                 "<td>{$field['type']}</td>".
                 "<td>{$input_field}</td>".
                 "<td>{$hidden_type}</td></tr>";
        }
        $view->set('constructor_table_enabled', $form_enabled);
      }else {
        $constructor_table_body = "<span class='subtle'> This Class has an empty constructor,"
          .' press the button below to create an instance </span>';
        $view->set('constructor_table_enabled', true);
      }
      $view->set('constructor_table_body', $constructor_table_body);

      //Create table with instantiated objects of this class
      $objects = $controller->get_objects($element_name);
      if(!empty($objects) && count($objects) > 0){
        $objects_table = '<tr><th>Object ID</th><th>String Representation</th></tr>';
        for($i=0; $i < count($objects); $i++){
          // modification start
        	$object_string_representation = serialize($objects[$i]);
        	
        	$length = strlen($object_string_representation);
        	$separator = strpos($object_string_representation, "{");
        	$end = strrpos($object_string_representation, "}");
        	
        	$head = substr($object_string_representation, 0, $separator);
        	$body = substr($object_string_representation, $separator + 1, $end - $separator - 1);

          $head_info = explode(":", $head, -1);
        	
          $class_name_length = intval($head_info[1]);
          $class_name = substr($head_info[2], 1, $class_name_length);
        	$num_of_attributes = intval($head_info[3]);
        	
          
          $temp_body = $body;
          $stack = new Stack();
          for($j = 0; $j < $num_of_attributes; $j++) {
            $attribute_separator = strpos($temp_body, ";");

            $attribute_string = substr($temp_body, 0, $attribute_separator);
            $attribute_info = explode(":", $attribute_string);
            $attribute_length = intval($attribute_info[1]);
            $attribute_name = substr($attribute_info[2], $class_name_length + 3, $attribute_length - $class_name_length - 2);

            $temp_body = substr($temp_body, $attribute_separator + 1);

            $value_type = $temp_body[0];

            if($value_type == "s") {
              $value_start = strpos($temp_body, "\"");

              $value_head = substr($temp_body, 0, $value_start - 1);
              $value_info = explode(":", $value_head);
              $value_length = intval($value_info[1]);
              $value_name = substr($temp_body, $value_start + 1, $value_length);

              if($j == 0) {
                $objects_table .= "<tr><td>".$i."</td><td>".$attribute_name.": ".$value_name."</td></tr>";
              } else {
                $objects_table .= "<tr><td></td><td>".$attribute_name.": ".$value_name."</td></tr>";
              }

              $temp_body = substr($temp_body, $value_start + $value_length + 3);

            } else if($value_type == "a") { //array
              $value_start = strpos($temp_body, "{");
              $stack->push($temp_body[$value_start]); //push "{"
              $index = $value_start + 1;

              while(!$stack->is_empty()) {
                if($temp_body[$index] == "{") {
                  $stack->push("{");
                } else if($temp_body[$index] == "}") {
                  $stack->pop();
                }

                $index++;
              }

              $value_end = $index - 1;

              if($j == 0) {
                $objects_table .= "<tr><td>".$i."</td><td></td></tr>";
              } else {
                $objects_table .= "<tr><td></td><td></td></tr>";
              }

              $temp_body = substr($temp_body, $value_end + 1);

            } else if($value_type == "i" || $value_type == "d" || $value_type == "b") {
              $value_end = strpos($temp_body, ";");
              $value_name = substr($temp_body, 2, $value_end - 2);

              if($j == 0) {
                $objects_table .= "<tr><td>".$i."</td><td>".$attribute_name.": ".$value_name."</td></tr>";
              } else {
                $objects_table .= "<tr><td></td><td>".$attribute_name.": ".$value_name."</td></tr>";
              }

              $temp_body = substr($temp_body, $value_end + 1);

            } else if($value_type == "N") {
              $temp_body = substr($temp_body, 2);
            } else {
              //$value_type == "O"
              $value_start = strpos($temp_body, "{");
              $stack->push($temp_body[$value_start]); //push "{"
              $index = $value_start + 1;

              while(!$stack->is_empty()) {
                if($temp_body[$index] == "{") {
                  $stack->push("{");
                } else if($temp_body[$index] == "}") {
                  $stack->pop();
                }

                $index++;
              }

              $value_end = $index - 1;

              if($j == 0) {
                $objects_table .= "<tr><td>".$i."</td><td></td></tr>";
              } else {
                $objects_table .= "<tr><td></td><td></td></tr>";
              }
              
              $temp_body = substr($temp_body, $value_end + 1);

            }
          }

          $objects_table .= "<tr width=1 height=20></tr>"; // empty row to separate different objects
        	
          	//$objects_table .= '<tr><td>'.$i.'</td><td>'.serialize($objects[$i]).'</td></tr>';
        }
      } else{
        $objects_table = "<span class='subtle'> There are no created instances for this Class </span>";
      }
      $view->set('objects_table', $objects_table);

      $element_names = $controller->get_element_names();

      $numbers_of_elements = NULL;
      if(isset($element_names) && is_array($element_names)) {
        $numbers_of_elements = array();
        foreach($element_names as $e){
          $instances = $controller->get_objects($e);
          if(empty($instances)) {
            $numbers_of_elements[$e] = 0;
          } else {
            $numbers_of_elements[$e] = count($instances);
          }
        }
      }

      $messages = $controller->get_messages_clear();

      $instantiatable_info = NULL;
      if(isset($element_names) && is_array($element_names)) {
        $instantiatable_info = array();
        foreach($element_names as $e){
          if(can_instantiate($controller, $e)) {
            $instantiatable_info[$e] = true;
          } else {
            $instantiatable_info[$e] = false;
          }
        }
      }
      
      $layout_parameter_map = array('element_names' => $element_names,
                                    'numbers_of_elements' => $numbers_of_elements,
                                    'messages' => $messages,
                                    'instantiatable_info' => $instantiatable_info);

      $view->show_layout($layout_parameter_map);

    } else{
      $controller->set_message('Error showing Element: '.$element_name.' does not exist', true);
      Uigu2_Controller::redirect('index'); 
    }
  } else{
    $controller->set_message('Error showing Element: the Element name was not provided', true);
    Uigu2_Controller::redirect('index'); 
  }
}

function can_instantiate($controller, $element_name) {
  if($element = $controller->get_element($element_name)) {
    if(count($element['constructor_params']) > 0) {
      
      foreach($element['constructor_params'] as $p) {
        if(!isset($element['attributes'][$p])) {
          $field = $element['associations'][$p];
          //Parameter for an association must be an index ("ID") for an existing instance
          $instances = $controller->get_objects($field['type']);
          if(empty($instances) || count($instances) == 0) {
            return false;
          }
        }
      }

      return true;

    } else {
      return true;
    }
  } else {
    return false;
  }
}
