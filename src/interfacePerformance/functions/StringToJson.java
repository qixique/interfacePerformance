package interfacePerformance.functions;

import interfacePerformance.util.StrToJson;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

public class StringToJson extends AbstractFunction{
	
	 private static final List<String> desc = new LinkedList<String>();
	 private static final String KEY = "__StrToJson";
	 static {
	        desc.add("CUSTOM StrToJson function.");
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
		String result = StrToJson.StrJson(((CompoundVariable) values[0]).execute().trim().toString());
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
	}

}