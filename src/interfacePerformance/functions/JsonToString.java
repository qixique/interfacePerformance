package interfacePerformance.functions;

import interfacePerformance.util.jsonToStr;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

public class JsonToString extends AbstractFunction{
	
	 private static final List<String> desc = new LinkedList<String>();
	 private static final String KEY = "__JasonEncode";
	 static {
	        desc.add("CUSTOM JasonEncode function.");
	    }
	 private  Object[] values;

	@Override
	public List<String> getArgumentDesc() {
		// TODO Auto-generated method stub
		return desc;
	}

	@Override
	public String execute(SampleResult arg0, Sampler arg1)
			throws InvalidVariableException {
		// TODO Auto-generated method stub
		String result = jsonToStr.jsonStr(((CompoundVariable) values[0]).execute().trim().toString());
		return result;
	}

	@Override
	public String getReferenceKey() {
		// TODO Auto-generated method stub
		return KEY;
	}

	@Override
	public void setParameters(Collection<CompoundVariable> arg0)
			throws InvalidVariableException {
		values = arg0.toArray();
//		value=parm[0].toString();
//		value = new String(((CompoundVariable) parm[0]).execute().trim());
			// TODO Auto-generated method stub
		
	}

}