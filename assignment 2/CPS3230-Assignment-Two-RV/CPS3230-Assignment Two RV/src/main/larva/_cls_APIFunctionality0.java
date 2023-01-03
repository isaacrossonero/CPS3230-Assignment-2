package larva;


import main.Runner;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_APIFunctionality0 implements _callable{

public static PrintWriter pw; 
public static _cls_APIFunctionality0 root;

public static LinkedHashMap<_cls_APIFunctionality0,_cls_APIFunctionality0> _cls_APIFunctionality0_instances = new LinkedHashMap<_cls_APIFunctionality0,_cls_APIFunctionality0>();
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Eclipse Workspace\\CPS3230-Assignment Two RV/src/main/output_APIFunctionality.txt");

root = new _cls_APIFunctionality0();
_cls_APIFunctionality0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_APIFunctionality0 parent; //to remain null - this class does not have a parent!
int no_automata = 1;
 public int numberOfAlerts =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_APIFunctionality0() {
}

public void initialisation() {
}

public static _cls_APIFunctionality0 _get_cls_APIFunctionality0_inst() { synchronized(_cls_APIFunctionality0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_APIFunctionality0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_APIFunctionality0_instances){
_performLogic_apiFunctionalityProperty(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_APIFunctionality0[] a = new _cls_APIFunctionality0[1];
synchronized(_cls_APIFunctionality0_instances){
a = _cls_APIFunctionality0_instances.keySet().toArray(a);}
for (_cls_APIFunctionality0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_APIFunctionality0_instances){
_cls_APIFunctionality0_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_apiFunctionalityProperty = 15;

public void _performLogic_apiFunctionalityProperty(String _info, int... _event) {

_cls_APIFunctionality0.pw.println("[apiFunctionalityProperty]AUTOMATON::> apiFunctionalityProperty("+") STATE::>"+ _string_apiFunctionalityProperty(_state_id_apiFunctionalityProperty, 0));
_cls_APIFunctionality0.pw.flush();

if (0==1){}
else if (_state_id_apiFunctionalityProperty==12){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*purgeAlerts*/))){
		numberOfAlerts =0 ;

		_state_id_apiFunctionalityProperty = 14;//moving to state alertsPurged
		_goto_apiFunctionalityProperty(_info);
		}
		else if ((_occurredEvent(_event,14/*uploadCorrectAlert*/)) && (numberOfAlerts >=5 )){
		numberOfAlerts ++;

		_state_id_apiFunctionalityProperty = 12;//moving to state badState
		_goto_apiFunctionalityProperty(_info);
		}
}
else if (_state_id_apiFunctionalityProperty==15){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*uploadCorrectAlert*/)) && (numberOfAlerts <5 )){
		numberOfAlerts ++;

		_state_id_apiFunctionalityProperty = 13;//moving to state alertUploaded
		_goto_apiFunctionalityProperty(_info);
		}
		else if ((_occurredEvent(_event,12/*purgeAlerts*/))){
		numberOfAlerts =0 ;

		_state_id_apiFunctionalityProperty = 14;//moving to state alertsPurged
		_goto_apiFunctionalityProperty(_info);
		}
}
else if (_state_id_apiFunctionalityProperty==13){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*uploadCorrectAlert*/)) && (numberOfAlerts <5 )){
		numberOfAlerts ++;

		_state_id_apiFunctionalityProperty = 13;//moving to state alertUploaded
		_goto_apiFunctionalityProperty(_info);
		}
		else if ((_occurredEvent(_event,12/*purgeAlerts*/))){
		numberOfAlerts =0 ;

		_state_id_apiFunctionalityProperty = 14;//moving to state alertsPurged
		_goto_apiFunctionalityProperty(_info);
		}
		else if ((_occurredEvent(_event,14/*uploadCorrectAlert*/)) && (numberOfAlerts ==5 )){
		numberOfAlerts ++;

		_state_id_apiFunctionalityProperty = 12;//moving to state badState
		_goto_apiFunctionalityProperty(_info);
		}
}
else if (_state_id_apiFunctionalityProperty==14){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*purgeAlerts*/))){
		numberOfAlerts =0 ;

		_state_id_apiFunctionalityProperty = 14;//moving to state alertsPurged
		_goto_apiFunctionalityProperty(_info);
		}
		else if ((_occurredEvent(_event,14/*uploadCorrectAlert*/)) && (numberOfAlerts ==0 )){
		numberOfAlerts ++;

		_state_id_apiFunctionalityProperty = 13;//moving to state alertUploaded
		_goto_apiFunctionalityProperty(_info);
		}
}
}

public void _goto_apiFunctionalityProperty(String _info){
_cls_APIFunctionality0.pw.println("[apiFunctionalityProperty]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_apiFunctionalityProperty(_state_id_apiFunctionalityProperty, 1));
_cls_APIFunctionality0.pw.flush();
}

public String _string_apiFunctionalityProperty(int _state_id, int _mode){
switch(_state_id){
case 12: if (_mode == 0) return "badState"; else return "!!!SYSTEM REACHED BAD STATE!!! badState "+new _BadStateExceptionAPIFunctionality().toString()+" ";
case 15: if (_mode == 0) return "apiInitialised"; else return "apiInitialised";
case 13: if (_mode == 0) return "alertUploaded"; else return "alertUploaded";
case 14: if (_mode == 0) return "alertsPurged"; else return "alertsPurged";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}