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
interface OperandParser {
    byte[] parse(byte operand, String[] operands, int lineNum, Map<String,Integer> lookupTable) throws ParserException;
}
