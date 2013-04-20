/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm.assembler;

import java.util.Map;

/**
 *
 * @author ryan
 */
public class NoOperandParser implements OperandParser{

    @Override
        public byte[] parse(byte opcode, String[] operands, int lineNum, Map<String, Integer> lookupTable) throws ParserException {
            byte[] vals = new byte[6];
            vals[0] = opcode;
            return vals;
        }
    
}
