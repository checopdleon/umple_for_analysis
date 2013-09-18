/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.17.0.2716 modeling language!*/

package cruise.umple.compiler;
import java.util.*;

/**
 * Specifies, within a state, what happens with a certain event (method call) occurs
 * Normally the state machine will go to a new state after executing various actions and
 * activities.
 * The guard is a boolean condition that may prevent triggering the transition
 * @umplesource StateMachine.ump 106
 * @umplesource StateMachine_Code.ump 435
 */
// line 106 "../../../../src/StateMachine.ump"
// line 435 "../../../../src/StateMachine_Code.ump"
public class Transition
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Transition Attributes
  private boolean isInternal;
  private boolean autoTransition;

  //Transition Associations
  private Event event;
  private State fromState;
  private State nextState;
  private Guard guard;
  private Action action;
  private List<StateMachineTraceItem> stateMachineTraceItems;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetEvent;
  private boolean canSetNextState;
  private boolean canSetGuard;
  private boolean canSetAction;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Transition(State aFromState, State aNextState)
  {
    cachedHashCode = -1;
    canSetEvent = true;
    canSetNextState = true;
    canSetGuard = true;
    canSetAction = true;
    isInternal = false;
    autoTransition = false;
    boolean didAddFromState = setFromState(aFromState);
    if (!didAddFromState)
    {
      throw new RuntimeException("Unable to create transition due to fromState");
    }
    boolean didAddNextState = setNextState(aNextState);
    if (!didAddNextState)
    {
      throw new RuntimeException("Unable to create nextTransition due to nextState");
    }
    stateMachineTraceItems = new ArrayList<StateMachineTraceItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsInternal(boolean aIsInternal)
  {
    boolean wasSet = false;
    isInternal = aIsInternal;
    wasSet = true;
    return wasSet;
  }

  public boolean setAutoTransition(boolean aAutoTransition)
  {
    boolean wasSet = false;
    autoTransition = aAutoTransition;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsInternal()
  {
    return isInternal;
  }

  public boolean getAutoTransition()
  {
    return autoTransition;
  }

  public boolean isIsInternal()
  {
    return isInternal;
  }

  public boolean isAutoTransition()
  {
    return autoTransition;
  }

  public Event getEvent()
  {
    return event;
  }

  public State getFromState()
  {
    return fromState;
  }

  public State getNextState()
  {
    return nextState;
  }

  public Guard getGuard()
  {
    return guard;
  }

  public Action getAction()
  {
    return action;
  }

  public StateMachineTraceItem getStateMachineTraceItem(int index)
  {
    StateMachineTraceItem aStateMachineTraceItem = stateMachineTraceItems.get(index);
    return aStateMachineTraceItem;
  }

  public List<StateMachineTraceItem> getStateMachineTraceItems()
  {
    List<StateMachineTraceItem> newStateMachineTraceItems = Collections.unmodifiableList(stateMachineTraceItems);
    return newStateMachineTraceItems;
  }

  public int numberOfStateMachineTraceItems()
  {
    int number = stateMachineTraceItems.size();
    return number;
  }

  public boolean hasStateMachineTraceItems()
  {
    boolean has = stateMachineTraceItems.size() > 0;
    return has;
  }

  public int indexOfStateMachineTraceItem(StateMachineTraceItem aStateMachineTraceItem)
  {
    int index = stateMachineTraceItems.indexOf(aStateMachineTraceItem);
    return index;
  }

  public boolean setEvent(Event aNewEvent)
  {
    boolean wasSet = false;
    if (!canSetEvent) { return false; }
    event = aNewEvent;
    wasSet = true;
    return wasSet;
  }

  public boolean setFromState(State aFromState)
  {
    boolean wasSet = false;
    if (aFromState == null)
    {
      return wasSet;
    }

    State existingFromState = fromState;
    fromState = aFromState;
    if (existingFromState != null && !existingFromState.equals(aFromState))
    {
      existingFromState.removeTransition(this);
    }
    fromState.addTransition(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setNextState(State aNextState)
  {
    boolean wasSet = false;
    if (!canSetNextState) { return false; }
    if (aNextState == null)
    {
      return wasSet;
    }

    State existingNextState = nextState;
    nextState = aNextState;
    if (existingNextState != null && !existingNextState.equals(aNextState))
    {
      existingNextState.removeNextTransition(this);
    }
    nextState.addNextTransition(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setGuard(Guard aNewGuard)
  {
    boolean wasSet = false;
    if (!canSetGuard) { return false; }
    guard = aNewGuard;
    wasSet = true;
    return wasSet;
  }

  public boolean setAction(Action aNewAction)
  {
    boolean wasSet = false;
    if (!canSetAction) { return false; }
    action = aNewAction;
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfStateMachineTraceItems()
  {
    return 0;
  }

  public boolean addStateMachineTraceItem(StateMachineTraceItem aStateMachineTraceItem)
  {
    boolean wasAdded = false;
    if (stateMachineTraceItems.contains(aStateMachineTraceItem)) { return false; }
    Transition existingTransition = aStateMachineTraceItem.getTransition();
    if (existingTransition == null)
    {
      aStateMachineTraceItem.setTransition(this);
    }
    else if (!this.equals(existingTransition))
    {
      existingTransition.removeStateMachineTraceItem(aStateMachineTraceItem);
      addStateMachineTraceItem(aStateMachineTraceItem);
    }
    else
    {
      stateMachineTraceItems.add(aStateMachineTraceItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStateMachineTraceItem(StateMachineTraceItem aStateMachineTraceItem)
  {
    boolean wasRemoved = false;
    if (stateMachineTraceItems.contains(aStateMachineTraceItem))
    {
      stateMachineTraceItems.remove(aStateMachineTraceItem);
      aStateMachineTraceItem.setTransition(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addStateMachineTraceItemAt(StateMachineTraceItem aStateMachineTraceItem, int index)
  {  
    boolean wasAdded = false;
    if(addStateMachineTraceItem(aStateMachineTraceItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStateMachineTraceItems()) { index = numberOfStateMachineTraceItems() - 1; }
      stateMachineTraceItems.remove(aStateMachineTraceItem);
      stateMachineTraceItems.add(index, aStateMachineTraceItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStateMachineTraceItemAt(StateMachineTraceItem aStateMachineTraceItem, int index)
  {
    boolean wasAdded = false;
    if(stateMachineTraceItems.contains(aStateMachineTraceItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStateMachineTraceItems()) { index = numberOfStateMachineTraceItems() - 1; }
      stateMachineTraceItems.remove(aStateMachineTraceItem);
      stateMachineTraceItems.add(index, aStateMachineTraceItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStateMachineTraceItemAt(aStateMachineTraceItem, index);
    }
    return wasAdded;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    Transition compareTo = (Transition)obj;
  
    if (event == null && compareTo.event != null)
    {
      return false;
    }
    else if (event != null && !event.equals(compareTo.event))
    {
      return false;
    }

    if (nextState == null && compareTo.nextState != null)
    {
      return false;
    }
    else if (nextState != null && !nextState.equals(compareTo.nextState))
    {
      return false;
    }

    if (guard == null && compareTo.guard != null)
    {
      return false;
    }
    else if (guard != null && !guard.equals(compareTo.guard))
    {
      return false;
    }

    if (action == null && compareTo.action != null)
    {
      return false;
    }
    else if (action != null && !action.equals(compareTo.action))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (event != null)
    {
      cachedHashCode = cachedHashCode * 23 + event.hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (nextState != null)
    {
      cachedHashCode = cachedHashCode * 23 + nextState.hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (guard != null)
    {
      cachedHashCode = cachedHashCode * 23 + guard.hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (action != null)
    {
      cachedHashCode = cachedHashCode * 23 + action.hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetEvent = false;
    canSetNextState = false;
    canSetGuard = false;
    canSetAction = false;
    return cachedHashCode;
  }

  public void delete()
  {
    event = null;
    State placeholderFromState = fromState;
    this.fromState = null;
    placeholderFromState.removeTransition(this);
    State placeholderNextState = nextState;
    this.nextState = null;
    placeholderNextState.removeNextTransition(this);
    guard = null;
    action = null;
    for(StateMachineTraceItem aStateMachineTraceItem : stateMachineTraceItems)
    {
      aStateMachineTraceItem.setTransition(null);
    }
  }

  @umplesourcefile(line={437},file={"StateMachine_Code.ump"},javaline={414},length={5})
   public static  Transition createPlaceholder(State nextState){
    StateMachine nullSm = new StateMachine("null");
    State nullState = new State("null",nullSm);
    return new Transition(nullState, nextState);
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "isInternal" + ":" + getIsInternal()+ "," +
            "autoTransition" + ":" + getAutoTransition()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "event = "+(getEvent()!=null?Integer.toHexString(System.identityHashCode(getEvent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "fromState = "+(getFromState()!=null?Integer.toHexString(System.identityHashCode(getFromState())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "nextState = "+(getNextState()!=null?Integer.toHexString(System.identityHashCode(getNextState())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "guard = "+(getGuard()!=null?Integer.toHexString(System.identityHashCode(getGuard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "action = "+(getAction()!=null?Integer.toHexString(System.identityHashCode(getAction())):"null")
     + outputString;
  }
}