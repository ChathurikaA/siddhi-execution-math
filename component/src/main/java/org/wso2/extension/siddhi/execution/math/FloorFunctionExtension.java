/*
 * Copyright (c)  2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.extension.siddhi.execution.math;

import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.exception.SiddhiAppRuntimeException;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.exception.SiddhiAppValidationException;

import java.util.Map;

/**
 * floor(a)
 * A Class which is used to calculate the floor value.
 * Accept Type(s): INT/LONG/FLOAT/DOUBLE
 * Return Type(s): DOUBLE
 */
@Extension(
        name = "floor",
        namespace = "math",
        description = "This function wraps the java.lang.Math.floor() function that returns the largest (closest to " +
                "positive infinity) value that is less that or equal to p1, and is equal to a mathematical integer.",
        parameters = {
                @Parameter(
                        name = "p1",
                        description = "The value of whose floor value should be found",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE})
        },
        returnAttributes = @ReturnAttribute(
                description = "The largest (closest to positive infinity) double value that is less than or " +
                        "equal to the p1 argument, and is equal to a mathematical integer",
                type = {DataType.DOUBLE}),
        examples = @Example(
                description = "floor(10.23) returns 10.0.",
                syntax = "define stream InValueStream (inValue double); \n" +
                        "from InValueStream \n" +
                        "select math:floor(inValue) as floorValue \n" +
                        "insert into OutMediationStream;")
)
public class FloorFunctionExtension extends FunctionExecutor {

    @Override
    protected void init(ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if (attributeExpressionExecutors.length != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to math:floor() function, " +
                    "required 1, but found " + attributeExpressionExecutors.length);
        }
        Attribute.Type attributeType = attributeExpressionExecutors[0].getReturnType();
        if (!((attributeType == Attribute.Type.DOUBLE)
                || (attributeType == Attribute.Type.INT)
                || (attributeType == Attribute.Type.FLOAT)
                || (attributeType == Attribute.Type.LONG))) {
            throw new SiddhiAppValidationException("Invalid parameter type found for the argument of math:floor() " +
                    "function, required " + Attribute.Type.INT + " or " + Attribute.Type.LONG +
                    " or " + Attribute.Type.FLOAT + " or " + Attribute.Type.DOUBLE +
                    ", but found " + attributeType.toString());
        }
    }

    @Override
    protected Object execute(Object[] data) {
        return null;    // This method won't get called. Hence, unimplemented.
    }

    @Override
    protected Object execute(Object data) {
        if (data != null) {
            //type-conversion
            if (data instanceof Integer) {
                int inputInt = (Integer) data;
                return Math.floor((double) inputInt);
            } else if (data instanceof Long) {
                long inputLong = (Long) data;
                return Math.floor((double) inputLong);
            } else if (data instanceof Float) {
                float inputFloat = (Float) data;
                return Math.floor((double) inputFloat);
            } else if (data instanceof Double) {
                return Math.floor((Double) data);
            }
        } else {
            throw new SiddhiAppRuntimeException("Input to the math:floor() function cannot be null");
        }
        return null;
    }

    @Override
    public void start() {
        //Nothing to start.
    }

    @Override
    public void stop() {
        //Nothing to stop.
    }

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.DOUBLE;
    }

    @Override
    public Map<String, Object> currentState() {
        return null;
    }

    @Override
    public void restoreState(Map<String, Object> map) {

    }
}
