package cruise.umple.ignore;

public class Ignore
{
  protected static String nl;
  public static synchronized Ignore create(String lineSeparator)
  {
    nl = lineSeparator;
    Ignore result = new Ignore();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "  public function ";
  protected final String TEXT_2 = "($";
  protected final String TEXT_3 = ")" + NL + "  {" + NL + "    $wasAdded = false;";
  protected final String TEXT_4 = NL + "    $this->";
  protected final String TEXT_5 = "[] = $";
  protected final String TEXT_6 = ";" + NL + "    $wasAdded = true;";
  protected final String TEXT_7 = NL + "    return $wasAdded;" + NL + "  }" + NL + "" + NL + "  public function ";
  protected final String TEXT_8 = "($";
  protected final String TEXT_9 = ")" + NL + "  {" + NL + "    $wasRemoved = false;";
  protected final String TEXT_10 = NL + "    unset($this->";
  protected final String TEXT_11 = "[$this->";
  protected final String TEXT_12 = "($";
  protected final String TEXT_13 = ")]);" + NL + "    $this->";
  protected final String TEXT_14 = " = array_values($this->";
  protected final String TEXT_15 = ");" + NL + "    $wasRemoved = true;";
  protected final String TEXT_16 = NL + "    return $wasRemoved;" + NL + "  }" + NL;
  protected final String TEXT_17 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(gen.translate("addMethod",av));
    stringBuffer.append(TEXT_2);
    stringBuffer.append(gen.translate("parameterOne",av));
    stringBuffer.append(TEXT_3);
     if (customAddPrefixCode != null) { append(stringBuffer, "\n{0}",GeneratorHelper.doIndent(customAddPrefixCode, "    ")); } 
    stringBuffer.append(TEXT_4);
    stringBuffer.append(gen.translate("attributeMany",av));
    stringBuffer.append(TEXT_5);
    stringBuffer.append(gen.translate("parameterOne",av));
    stringBuffer.append(TEXT_6);
     if (customAddPostfixCode != null) { append(stringBuffer, "\n{0}",GeneratorHelper.doIndent(customAddPostfixCode, "    ")); } 
    stringBuffer.append(TEXT_7);
    stringBuffer.append(gen.translate("removeMethod",av));
    stringBuffer.append(TEXT_8);
    stringBuffer.append(gen.translate("parameterOne",av));
    stringBuffer.append(TEXT_9);
     if (customRemovePrefixCode != null) { append(stringBuffer, "\n{0}",GeneratorHelper.doIndent(customRemovePrefixCode, "    ")); } 
    stringBuffer.append(TEXT_10);
    stringBuffer.append(gen.translate("attributeMany",av));
    stringBuffer.append(TEXT_11);
    stringBuffer.append(gen.translate("indexOfMethod",av));
    stringBuffer.append(TEXT_12);
    stringBuffer.append(gen.translate("parameterOne",av));
    stringBuffer.append(TEXT_13);
    stringBuffer.append(gen.translate("attributeMany",av));
    stringBuffer.append(TEXT_14);
    stringBuffer.append(gen.translate("attributeMany",av));
    stringBuffer.append(TEXT_15);
     if (customRemovePostfixCode != null) { append(stringBuffer, "\n{0}",GeneratorHelper.doIndent(customRemovePostfixCode, "    ")); } 
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    return stringBuffer.toString();
  }
}
