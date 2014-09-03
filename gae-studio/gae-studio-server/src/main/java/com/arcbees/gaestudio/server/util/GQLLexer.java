/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

// $ANTLR 3.4 GQL.g 2014-07-02 15:17:15

package com.arcbees.gaestudio.server.util;

import org.antlr.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class GQLLexer extends Lexer {
    public static final int EOF = -1;
    public static final int T__55 = 55;
    public static final int T__56 = 56;
    public static final int T__57 = 57;
    public static final int T__58 = 58;
    public static final int T__59 = 59;
    public static final int T__60 = 60;
    public static final int T__61 = 61;
    public static final int T__62 = 62;
    public static final int T__63 = 63;
    public static final int T__64 = 64;
    public static final int T__65 = 65;
    public static final int T__66 = 66;
    public static final int A = 4;
    public static final int ANCESTOR = 5;
    public static final int AND = 6;
    public static final int ASC = 7;
    public static final int B = 8;
    public static final int BOOLEAN = 9;
    public static final int BY = 10;
    public static final int C = 11;
    public static final int COMMENT = 12;
    public static final int D = 13;
    public static final int DECIMAL = 14;
    public static final int DESC = 15;
    public static final int DIGIT = 16;
    public static final int DOT = 17;
    public static final int E = 18;
    public static final int F = 19;
    public static final int FROM = 20;
    public static final int FUNCTION = 21;
    public static final int G = 22;
    public static final int H = 23;
    public static final int I = 24;
    public static final int IDENTITY = 25;
    public static final int IN = 26;
    public static final int IS = 27;
    public static final int J = 28;
    public static final int K = 29;
    public static final int L = 30;
    public static final int LETTER = 31;
    public static final int LIMIT = 32;
    public static final int M = 33;
    public static final int MULTILINE_COMMENT = 34;
    public static final int N = 35;
    public static final int NULL = 36;
    public static final int O = 37;
    public static final int OFFSET = 38;
    public static final int ORDER = 39;
    public static final int P = 40;
    public static final int Q = 41;
    public static final int R = 42;
    public static final int S = 43;
    public static final int SELECT = 44;
    public static final int STRING_LITERAL = 45;
    public static final int T = 46;
    public static final int U = 47;
    public static final int V = 48;
    public static final int W = 49;
    public static final int WHERE = 50;
    public static final int WHITESPACE = 51;
    public static final int X = 52;
    public static final int Y = 53;
    public static final int Z = 54;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[]{};
    }

    public GQLLexer() {
    }

    public GQLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }

    public GQLLexer(CharStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String getGrammarFileName() {
        return "GQL.g";
    }

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:19:7: ( '!=' )
            // GQL.g:19:9: '!='
            {
                match("!=");


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:20:7: ( '(' )
            // GQL.g:20:9: '('
            {
                match('(');

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:21:7: ( ')' )
            // GQL.g:21:9: ')'
            {
                match(')');

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:22:7: ( '*' )
            // GQL.g:22:9: '*'
            {
                match('*');

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:23:7: ( ',' )
            // GQL.g:23:9: ','
            {
                match(',');

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:24:7: ( ':' )
            // GQL.g:24:9: ':'
            {
                match(':');

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:25:7: ( '<' )
            // GQL.g:25:9: '<'
            {
                match('<');

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:26:7: ( '<=' )
            // GQL.g:26:9: '<='
            {
                match("<=");


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:27:7: ( '=' )
            // GQL.g:27:9: '='
            {
                match('=');

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:28:7: ( '>' )
            // GQL.g:28:9: '>'
            {
                match('>');

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:29:7: ( '>=' )
            // GQL.g:29:9: '>='
            {
                match(">=");


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:30:7: ( '__key__' )
            // GQL.g:30:9: '__key__'
            {
                match("__key__");


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "SELECT"
    public final void mSELECT() throws RecognitionException {
        try {
            int _type = SELECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:122:8: ( S E L E C T )
            // GQL.g:122:10: S E L E C T
            {
                mS();


                mE();


                mL();


                mE();


                mC();


                mT();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "SELECT"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:124:6: ( F R O M )
            // GQL.g:124:8: F R O M
            {
                mF();


                mR();


                mO();


                mM();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "FROM"

    // $ANTLR start "WHERE"
    public final void mWHERE() throws RecognitionException {
        try {
            int _type = WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:126:7: ( W H E R E )
            // GQL.g:126:9: W H E R E
            {
                mW();


                mH();


                mE();


                mR();


                mE();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "WHERE"

    // $ANTLR start "ORDER"
    public final void mORDER() throws RecognitionException {
        try {
            int _type = ORDER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:128:7: ( O R D E R )
            // GQL.g:128:9: O R D E R
            {
                mO();


                mR();


                mD();


                mE();


                mR();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "ORDER"

    // $ANTLR start "BY"
    public final void mBY() throws RecognitionException {
        try {
            int _type = BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:130:4: ( B Y )
            // GQL.g:130:6: B Y
            {
                mB();


                mY();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "BY"

    // $ANTLR start "ASC"
    public final void mASC() throws RecognitionException {
        try {
            int _type = ASC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:132:5: ( A S C )
            // GQL.g:132:7: A S C
            {
                mA();


                mS();


                mC();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "ASC"

    // $ANTLR start "DESC"
    public final void mDESC() throws RecognitionException {
        try {
            int _type = DESC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:134:6: ( D E S C )
            // GQL.g:134:8: D E S C
            {
                mD();


                mE();


                mS();


                mC();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "DESC"

    // $ANTLR start "LIMIT"
    public final void mLIMIT() throws RecognitionException {
        try {
            int _type = LIMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:136:7: ( L I M I T )
            // GQL.g:136:9: L I M I T
            {
                mL();


                mI();


                mM();


                mI();


                mT();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "LIMIT"

    // $ANTLR start "OFFSET"
    public final void mOFFSET() throws RecognitionException {
        try {
            int _type = OFFSET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:138:8: ( O F F S E T )
            // GQL.g:138:10: O F F S E T
            {
                mO();


                mF();


                mF();


                mS();


                mE();


                mT();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "OFFSET"

    // $ANTLR start "ANCESTOR"
    public final void mANCESTOR() throws RecognitionException {
        try {
            int _type = ANCESTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:140:9: ( A N C E S T O R )
            // GQL.g:140:11: A N C E S T O R
            {
                mA();


                mN();


                mC();


                mE();


                mS();


                mT();


                mO();


                mR();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "ANCESTOR"

    // $ANTLR start "IS"
    public final void mIS() throws RecognitionException {
        try {
            int _type = IS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:142:3: ( I S )
            // GQL.g:142:5: I S
            {
                mI();


                mS();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "IS"

    // $ANTLR start "FUNCTION"
    public final void mFUNCTION() throws RecognitionException {
        try {
            int _type = FUNCTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:148:3: ( D A T E T I M E | D A T E | T I M E | K E Y | U S E R | G E O P T )
            int alt1 = 6;
            switch (input.LA(1)) {
                case 'D':
                case 'd': {
                    int LA1_1 = input.LA(2);

                    if ((LA1_1 == 'A' || LA1_1 == 'a')) {
                        int LA1_6 = input.LA(3);

                        if ((LA1_6 == 'T' || LA1_6 == 't')) {
                            int LA1_7 = input.LA(4);

                            if ((LA1_7 == 'E' || LA1_7 == 'e')) {
                                int LA1_8 = input.LA(5);

                                if ((LA1_8 == 'T' || LA1_8 == 't')) {
                                    alt1 = 1;
                                } else {
                                    alt1 = 2;
                                }
                            } else {
                                NoViableAltException nvae =
                                        new NoViableAltException("", 1, 7, input);

                                throw nvae;

                            }
                        } else {
                            NoViableAltException nvae =
                                    new NoViableAltException("", 1, 6, input);

                            throw nvae;

                        }
                    } else {
                        NoViableAltException nvae =
                                new NoViableAltException("", 1, 1, input);

                        throw nvae;

                    }
                }
                break;
                case 'T':
                case 't': {
                    alt1 = 3;
                }
                break;
                case 'K':
                case 'k': {
                    alt1 = 4;
                }
                break;
                case 'U':
                case 'u': {
                    alt1 = 5;
                }
                break;
                case 'G':
                case 'g': {
                    alt1 = 6;
                }
                break;
                default:
                    NoViableAltException nvae =
                            new NoViableAltException("", 1, 0, input);

                    throw nvae;

            }

            switch (alt1) {
                case 1:
                    // GQL.g:148:5: D A T E T I M E
                {
                    mD();


                    mA();


                    mT();


                    mE();


                    mT();


                    mI();


                    mM();


                    mE();


                }
                break;
                case 2:
                    // GQL.g:151:5: D A T E
                {
                    mD();


                    mA();


                    mT();


                    mE();


                }
                break;
                case 3:
                    // GQL.g:154:5: T I M E
                {
                    mT();


                    mI();


                    mM();


                    mE();


                }
                break;
                case 4:
                    // GQL.g:157:5: K E Y
                {
                    mK();


                    mE();


                    mY();


                }
                break;
                case 5:
                    // GQL.g:159:5: U S E R
                {
                    mU();


                    mS();


                    mE();


                    mR();


                }
                break;
                case 6:
                    // GQL.g:161:5: G E O P T
                {
                    mG();


                    mE();


                    mO();


                    mP();


                    mT();


                }
                break;

            }
            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "FUNCTION"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:165:4: ( A N D )
            // GQL.g:165:6: A N D
            {
                mA();


                mN();


                mD();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "IN"
    public final void mIN() throws RecognitionException {
        try {
            int _type = IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:167:3: ( I N )
            // GQL.g:167:5: I N
            {
                mI();


                mN();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "IN"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:174:3: ( '\\'' ( '\\\\' '\\'' |~ ( '\\'' | '\\n' | '\\r' ) )* '\\'' )
            // GQL.g:174:5: '\\'' ( '\\\\' '\\'' |~ ( '\\'' | '\\n' | '\\r' ) )* '\\''
            {
                match('\'');

                // GQL.g:175:5: ( '\\\\' '\\'' |~ ( '\\'' | '\\n' | '\\r' ) )*
                loop2:
                do {
                    int alt2 = 3;
                    int LA2_0 = input.LA(1);

                    if ((LA2_0 == '\\')) {
                        int LA2_2 = input.LA(2);

                        if ((LA2_2 == '\'')) {
                            int LA2_4 = input.LA(3);

                            if (((LA2_4 >= '\u0000' && LA2_4 <= '\t') || (LA2_4 >= '\u000B' && LA2_4 <= '\f') || (LA2_4 >= '\u000E' && LA2_4 <= '\uFFFF'))) {
                                alt2 = 1;
                            } else {
                                alt2 = 2;
                            }


                        } else if (((LA2_2 >= '\u0000' && LA2_2 <= '\t') || (LA2_2 >= '\u000B' && LA2_2 <= '\f') || (LA2_2 >= '\u000E' && LA2_2 <= '&') || (LA2_2 >= '(' && LA2_2 <= '\uFFFF'))) {
                            alt2 = 2;
                        }


                    } else if (((LA2_0 >= '\u0000' && LA2_0 <= '\t') || (LA2_0 >= '\u000B' && LA2_0 <= '\f') || (LA2_0 >= '\u000E' && LA2_0 <= '&') || (LA2_0 >= '(' && LA2_0 <= '[') || (LA2_0 >= ']' && LA2_0 <= '\uFFFF'))) {
                        alt2 = 2;
                    }


                    switch (alt2) {
                        case 1:
                            // GQL.g:175:7: '\\\\' '\\''
                        {
                            match('\\');

                            match('\'');

                        }
                        break;
                        case 2:
                            // GQL.g:176:7: ~ ( '\\'' | '\\n' | '\\r' )
                        {
                            if ((input.LA(1) >= '\u0000' && input.LA(1) <= '\t') || (input.LA(1) >= '\u000B' && input.LA(1) <= '\f') || (input.LA(1) >= '\u000E' && input.LA(1) <= '&') || (input.LA(1) >= '(' && input.LA(1) <= '\uFFFF')) {
                                input.consume();
                            } else {
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                        default:
                            break loop2;
                    }
                } while (true);


                match('\'');

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "STRING_LITERAL"

    // $ANTLR start "A"
    public final void mA() throws RecognitionException {
        try {
            // GQL.g:181:12: ( 'a' | 'A' )
            // GQL.g:
            {
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "A"

    // $ANTLR start "B"
    public final void mB() throws RecognitionException {
        try {
            // GQL.g:182:12: ( 'b' | 'B' )
            // GQL.g:
            {
                if (input.LA(1) == 'B' || input.LA(1) == 'b') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "B"

    // $ANTLR start "C"
    public final void mC() throws RecognitionException {
        try {
            // GQL.g:183:12: ( 'c' | 'C' )
            // GQL.g:
            {
                if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "C"

    // $ANTLR start "D"
    public final void mD() throws RecognitionException {
        try {
            // GQL.g:184:12: ( 'd' | 'D' )
            // GQL.g:
            {
                if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "D"

    // $ANTLR start "E"
    public final void mE() throws RecognitionException {
        try {
            // GQL.g:185:12: ( 'e' | 'E' )
            // GQL.g:
            {
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "E"

    // $ANTLR start "F"
    public final void mF() throws RecognitionException {
        try {
            // GQL.g:186:12: ( 'f' | 'F' )
            // GQL.g:
            {
                if (input.LA(1) == 'F' || input.LA(1) == 'f') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "F"

    // $ANTLR start "G"
    public final void mG() throws RecognitionException {
        try {
            // GQL.g:187:12: ( 'g' | 'G' )
            // GQL.g:
            {
                if (input.LA(1) == 'G' || input.LA(1) == 'g') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "G"

    // $ANTLR start "H"
    public final void mH() throws RecognitionException {
        try {
            // GQL.g:188:12: ( 'h' | 'H' )
            // GQL.g:
            {
                if (input.LA(1) == 'H' || input.LA(1) == 'h') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "H"

    // $ANTLR start "I"
    public final void mI() throws RecognitionException {
        try {
            // GQL.g:189:12: ( 'i' | 'I' )
            // GQL.g:
            {
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "I"

    // $ANTLR start "J"
    public final void mJ() throws RecognitionException {
        try {
            // GQL.g:190:12: ( 'j' | 'J' )
            // GQL.g:
            {
                if (input.LA(1) == 'J' || input.LA(1) == 'j') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "J"

    // $ANTLR start "K"
    public final void mK() throws RecognitionException {
        try {
            // GQL.g:191:12: ( 'k' | 'K' )
            // GQL.g:
            {
                if (input.LA(1) == 'K' || input.LA(1) == 'k') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "K"

    // $ANTLR start "L"
    public final void mL() throws RecognitionException {
        try {
            // GQL.g:192:12: ( 'l' | 'L' )
            // GQL.g:
            {
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "L"

    // $ANTLR start "M"
    public final void mM() throws RecognitionException {
        try {
            // GQL.g:193:12: ( 'm' | 'M' )
            // GQL.g:
            {
                if (input.LA(1) == 'M' || input.LA(1) == 'm') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "M"

    // $ANTLR start "N"
    public final void mN() throws RecognitionException {
        try {
            // GQL.g:194:12: ( 'n' | 'N' )
            // GQL.g:
            {
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "N"

    // $ANTLR start "O"
    public final void mO() throws RecognitionException {
        try {
            // GQL.g:195:12: ( 'o' | 'O' )
            // GQL.g:
            {
                if (input.LA(1) == 'O' || input.LA(1) == 'o') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "O"

    // $ANTLR start "P"
    public final void mP() throws RecognitionException {
        try {
            // GQL.g:196:12: ( 'p' | 'P' )
            // GQL.g:
            {
                if (input.LA(1) == 'P' || input.LA(1) == 'p') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "P"

    // $ANTLR start "Q"
    public final void mQ() throws RecognitionException {
        try {
            // GQL.g:197:12: ( 'q' | 'Q' )
            // GQL.g:
            {
                if (input.LA(1) == 'Q' || input.LA(1) == 'q') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "Q"

    // $ANTLR start "R"
    public final void mR() throws RecognitionException {
        try {
            // GQL.g:198:12: ( 'r' | 'R' )
            // GQL.g:
            {
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "R"

    // $ANTLR start "S"
    public final void mS() throws RecognitionException {
        try {
            // GQL.g:199:12: ( 's' | 'S' )
            // GQL.g:
            {
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "S"

    // $ANTLR start "T"
    public final void mT() throws RecognitionException {
        try {
            // GQL.g:200:12: ( 't' | 'T' )
            // GQL.g:
            {
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "T"

    // $ANTLR start "U"
    public final void mU() throws RecognitionException {
        try {
            // GQL.g:201:12: ( 'u' | 'U' )
            // GQL.g:
            {
                if (input.LA(1) == 'U' || input.LA(1) == 'u') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "U"

    // $ANTLR start "V"
    public final void mV() throws RecognitionException {
        try {
            // GQL.g:202:12: ( 'v' | 'V' )
            // GQL.g:
            {
                if (input.LA(1) == 'V' || input.LA(1) == 'v') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "V"

    // $ANTLR start "W"
    public final void mW() throws RecognitionException {
        try {
            // GQL.g:203:12: ( 'w' | 'W' )
            // GQL.g:
            {
                if (input.LA(1) == 'W' || input.LA(1) == 'w') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "W"

    // $ANTLR start "X"
    public final void mX() throws RecognitionException {
        try {
            // GQL.g:204:12: ( 'x' | 'X' )
            // GQL.g:
            {
                if (input.LA(1) == 'X' || input.LA(1) == 'x') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "X"

    // $ANTLR start "Y"
    public final void mY() throws RecognitionException {
        try {
            // GQL.g:205:12: ( 'y' | 'Y' )
            // GQL.g:
            {
                if (input.LA(1) == 'Y' || input.LA(1) == 'y') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "Y"

    // $ANTLR start "Z"
    public final void mZ() throws RecognitionException {
        try {
            // GQL.g:206:12: ( 'z' | 'Z' )
            // GQL.g:
            {
                if (input.LA(1) == 'Z' || input.LA(1) == 'z') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "Z"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // GQL.g:207:17: ( 'a' .. 'z' | 'A' .. 'Z' )
            // GQL.g:
            {
                if ((input.LA(1) >= 'A' && input.LA(1) <= 'Z') || (input.LA(1) >= 'a' && input.LA(1) <= 'z')) {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // GQL.g:209:16: ( '0' .. '9' )
            // GQL.g:
            {
                if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            // GQL.g:211:14: ( '.' )
            // GQL.g:211:16: '.'
            {
                match('.');

            }


        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "DECIMAL"
    public final void mDECIMAL() throws RecognitionException {
        try {
            int _type = DECIMAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:213:9: ( ( DIGIT )* ( DOT ( DIGIT )+ )? )
            // GQL.g:213:11: ( DIGIT )* ( DOT ( DIGIT )+ )?
            {
                // GQL.g:213:11: ( DIGIT )*
                loop3:
                do {
                    int alt3 = 2;
                    int LA3_0 = input.LA(1);

                    if (((LA3_0 >= '0' && LA3_0 <= '9'))) {
                        alt3 = 1;
                    }


                    switch (alt3) {
                        case 1:
                            // GQL.g:
                        {
                            if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                                input.consume();
                            } else {
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                        default:
                            break loop3;
                    }
                } while (true);


                // GQL.g:213:19: ( DOT ( DIGIT )+ )?
                int alt5 = 2;
                int LA5_0 = input.LA(1);

                if ((LA5_0 == '.')) {
                    alt5 = 1;
                }
                switch (alt5) {
                    case 1:
                        // GQL.g:213:20: DOT ( DIGIT )+
                    {
                        mDOT();


                        // GQL.g:213:24: ( DIGIT )+
                        int cnt4 = 0;
                        loop4:
                        do {
                            int alt4 = 2;
                            int LA4_0 = input.LA(1);

                            if (((LA4_0 >= '0' && LA4_0 <= '9'))) {
                                alt4 = 1;
                            }


                            switch (alt4) {
                                case 1:
                                    // GQL.g:
                                {
                                    if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                                        input.consume();
                                    } else {
                                        MismatchedSetException mse = new MismatchedSetException(null, input);
                                        recover(mse);
                                        throw mse;
                                    }


                                }
                                break;

                                default:
                                    if (cnt4 >= 1) break loop4;
                                    EarlyExitException eee =
                                            new EarlyExitException(4, input);
                                    throw eee;
                            }
                            cnt4++;
                        } while (true);


                    }
                    break;

                }


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "DECIMAL"

    // $ANTLR start "BOOLEAN"
    public final void mBOOLEAN() throws RecognitionException {
        try {
            int _type = BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:215:9: ( T R U E | F A L S E )
            int alt6 = 2;
            int LA6_0 = input.LA(1);

            if ((LA6_0 == 'T' || LA6_0 == 't')) {
                alt6 = 1;
            } else if ((LA6_0 == 'F' || LA6_0 == 'f')) {
                alt6 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 6, 0, input);

                throw nvae;

            }
            switch (alt6) {
                case 1:
                    // GQL.g:215:11: T R U E
                {
                    mT();


                    mR();


                    mU();


                    mE();


                }
                break;
                case 2:
                    // GQL.g:215:21: F A L S E
                {
                    mF();


                    mA();


                    mL();


                    mS();


                    mE();


                }
                break;

            }
            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "BOOLEAN"

    // $ANTLR start "NULL"
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:217:6: ( N U L L )
            // GQL.g:217:8: N U L L
            {
                mN();


                mU();


                mL();


                mL();


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "NULL"

    // $ANTLR start "IDENTITY"
    public final void mIDENTITY() throws RecognitionException {
        try {
            int _type = IDENTITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:221:3: ( ( LETTER | '_' ) ( LETTER | DIGIT | '_' | '-' )* )
            // GQL.g:221:5: ( LETTER | '_' ) ( LETTER | DIGIT | '_' | '-' )*
            {
                if ((input.LA(1) >= 'A' && input.LA(1) <= 'Z') || input.LA(1) == '_' || (input.LA(1) >= 'a' && input.LA(1) <= 'z')) {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


                // GQL.g:221:20: ( LETTER | DIGIT | '_' | '-' )*
                loop7:
                do {
                    int alt7 = 2;
                    int LA7_0 = input.LA(1);

                    if ((LA7_0 == '-' || (LA7_0 >= '0' && LA7_0 <= '9') || (LA7_0 >= 'A' && LA7_0 <= 'Z') || LA7_0 == '_' || (LA7_0 >= 'a' && LA7_0 <= 'z'))) {
                        alt7 = 1;
                    }


                    switch (alt7) {
                        case 1:
                            // GQL.g:
                        {
                            if (input.LA(1) == '-' || (input.LA(1) >= '0' && input.LA(1) <= '9') || (input.LA(1) >= 'A' && input.LA(1) <= 'Z') || input.LA(1) == '_' || (input.LA(1) >= 'a' && input.LA(1) <= 'z')) {
                                input.consume();
                            } else {
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                        default:
                            break loop7;
                    }
                } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "IDENTITY"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:224:12: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\f' )+ )
            // GQL.g:224:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\f' )+
            {
                // GQL.g:224:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\f' )+
                int cnt8 = 0;
                loop8:
                do {
                    int alt8 = 2;
                    int LA8_0 = input.LA(1);

                    if (((LA8_0 >= '\t' && LA8_0 <= '\n') || (LA8_0 >= '\f' && LA8_0 <= '\r') || LA8_0 == ' ')) {
                        alt8 = 1;
                    }


                    switch (alt8) {
                        case 1:
                            // GQL.g:
                        {
                            if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                input.consume();
                            } else {
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                        default:
                            if (cnt8 >= 1) break loop8;
                            EarlyExitException eee =
                                    new EarlyExitException(8, input);
                            throw eee;
                    }
                    cnt8++;
                } while (true);


                _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "WHITESPACE"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:226:9: ( '//' ( . )* ( '\\n' | '\\r' ) )
            // GQL.g:226:11: '//' ( . )* ( '\\n' | '\\r' )
            {
                match("//");


                // GQL.g:226:16: ( . )*
                loop9:
                do {
                    int alt9 = 2;
                    int LA9_0 = input.LA(1);

                    if ((LA9_0 == '\n' || LA9_0 == '\r')) {
                        alt9 = 2;
                    } else if (((LA9_0 >= '\u0000' && LA9_0 <= '\t') || (LA9_0 >= '\u000B' && LA9_0 <= '\f') || (LA9_0 >= '\u000E' && LA9_0 <= '\uFFFF'))) {
                        alt9 = 1;
                    }


                    switch (alt9) {
                        case 1:
                            // GQL.g:226:16: .
                        {
                            matchAny();

                        }
                        break;

                        default:
                            break loop9;
                    }
                } while (true);


                if (input.LA(1) == '\n' || input.LA(1) == '\r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


                _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "MULTILINE_COMMENT"
    public final void mMULTILINE_COMMENT() throws RecognitionException {
        try {
            int _type = MULTILINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GQL.g:228:19: ( '/*' ( . )* '*/' )
            // GQL.g:228:21: '/*' ( . )* '*/'
            {
                match("/*");


                // GQL.g:228:26: ( . )*
                loop10:
                do {
                    int alt10 = 2;
                    int LA10_0 = input.LA(1);

                    if ((LA10_0 == '*')) {
                        int LA10_1 = input.LA(2);

                        if ((LA10_1 == '/')) {
                            alt10 = 2;
                        } else if (((LA10_1 >= '\u0000' && LA10_1 <= '.') || (LA10_1 >= '0' && LA10_1 <= '\uFFFF'))) {
                            alt10 = 1;
                        }


                    } else if (((LA10_0 >= '\u0000' && LA10_0 <= ')') || (LA10_0 >= '+' && LA10_0 <= '\uFFFF'))) {
                        alt10 = 1;
                    }


                    switch (alt10) {
                        case 1:
                            // GQL.g:228:26: .
                        {
                            matchAny();

                        }
                        break;

                        default:
                            break loop10;
                    }
                } while (true);


                match("*/");


                _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }
    // $ANTLR end "MULTILINE_COMMENT"

    public void mTokens() throws RecognitionException {
        // GQL.g:1:8: ( T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | SELECT | FROM | WHERE | ORDER | BY | ASC | DESC | LIMIT | OFFSET | ANCESTOR | IS | FUNCTION | AND | IN | STRING_LITERAL | DECIMAL | BOOLEAN | NULL | IDENTITY | WHITESPACE | COMMENT | MULTILINE_COMMENT )
        int alt11 = 34;
        alt11 = dfa11.predict(input);
        switch (alt11) {
            case 1:
                // GQL.g:1:10: T__55
            {
                mT__55();


            }
            break;
            case 2:
                // GQL.g:1:16: T__56
            {
                mT__56();


            }
            break;
            case 3:
                // GQL.g:1:22: T__57
            {
                mT__57();


            }
            break;
            case 4:
                // GQL.g:1:28: T__58
            {
                mT__58();


            }
            break;
            case 5:
                // GQL.g:1:34: T__59
            {
                mT__59();


            }
            break;
            case 6:
                // GQL.g:1:40: T__60
            {
                mT__60();


            }
            break;
            case 7:
                // GQL.g:1:46: T__61
            {
                mT__61();


            }
            break;
            case 8:
                // GQL.g:1:52: T__62
            {
                mT__62();


            }
            break;
            case 9:
                // GQL.g:1:58: T__63
            {
                mT__63();


            }
            break;
            case 10:
                // GQL.g:1:64: T__64
            {
                mT__64();


            }
            break;
            case 11:
                // GQL.g:1:70: T__65
            {
                mT__65();


            }
            break;
            case 12:
                // GQL.g:1:76: T__66
            {
                mT__66();


            }
            break;
            case 13:
                // GQL.g:1:82: SELECT
            {
                mSELECT();


            }
            break;
            case 14:
                // GQL.g:1:89: FROM
            {
                mFROM();


            }
            break;
            case 15:
                // GQL.g:1:94: WHERE
            {
                mWHERE();


            }
            break;
            case 16:
                // GQL.g:1:100: ORDER
            {
                mORDER();


            }
            break;
            case 17:
                // GQL.g:1:106: BY
            {
                mBY();


            }
            break;
            case 18:
                // GQL.g:1:109: ASC
            {
                mASC();


            }
            break;
            case 19:
                // GQL.g:1:113: DESC
            {
                mDESC();


            }
            break;
            case 20:
                // GQL.g:1:118: LIMIT
            {
                mLIMIT();


            }
            break;
            case 21:
                // GQL.g:1:124: OFFSET
            {
                mOFFSET();


            }
            break;
            case 22:
                // GQL.g:1:131: ANCESTOR
            {
                mANCESTOR();


            }
            break;
            case 23:
                // GQL.g:1:140: IS
            {
                mIS();


            }
            break;
            case 24:
                // GQL.g:1:143: FUNCTION
            {
                mFUNCTION();


            }
            break;
            case 25:
                // GQL.g:1:152: AND
            {
                mAND();


            }
            break;
            case 26:
                // GQL.g:1:156: IN
            {
                mIN();


            }
            break;
            case 27:
                // GQL.g:1:159: STRING_LITERAL
            {
                mSTRING_LITERAL();


            }
            break;
            case 28:
                // GQL.g:1:174: DECIMAL
            {
                mDECIMAL();


            }
            break;
            case 29:
                // GQL.g:1:182: BOOLEAN
            {
                mBOOLEAN();


            }
            break;
            case 30:
                // GQL.g:1:190: NULL
            {
                mNULL();


            }
            break;
            case 31:
                // GQL.g:1:195: IDENTITY
            {
                mIDENTITY();


            }
            break;
            case 32:
                // GQL.g:1:204: WHITESPACE
            {
                mWHITESPACE();


            }
            break;
            case 33:
                // GQL.g:1:215: COMMENT
            {
                mCOMMENT();


            }
            break;
            case 34:
                // GQL.g:1:223: MULTILINE_COMMENT
            {
                mMULTILINE_COMMENT();


            }
            break;

        }

    }


    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA11_eotS =
            "\1\31\6\uffff\1\37\1\uffff\1\41\16\33\2\uffff\1\33\7\uffff\7\33" +
                    "\1\100\5\33\1\107\1\110\6\33\2\uffff\7\33\1\uffff\1\126\1\33\1\130" +
                    "\3\33\2\uffff\2\33\1\136\5\33\1\144\4\33\1\uffff\1\33\1\uffff\1" +
                    "\152\1\136\1\33\1\136\1\155\1\uffff\1\136\1\33\1\157\2\33\1\uffff" +
                    "\1\155\1\162\1\163\2\33\1\uffff\1\33\1\167\1\uffff\1\136\1\uffff" +
                    "\1\33\1\171\2\uffff\1\172\2\33\1\uffff\1\175\2\uffff\2\33\1\uffff" +
                    "\1\u0080\1\136\1\uffff";
    static final String DFA11_eofS =
            "\u0081\uffff";
    static final String DFA11_minS =
            "\1\11\6\uffff\1\75\1\uffff\1\75\1\137\1\105\1\101\1\110\1\106\1" +
                    "\131\1\116\1\101\1\111\1\116\1\111\1\105\1\123\1\105\2\uffff\1\125" +
                    "\2\uffff\1\52\4\uffff\1\153\1\114\1\117\1\114\1\105\1\104\1\106" +
                    "\1\55\2\103\1\123\1\124\1\115\2\55\1\115\1\125\1\131\1\105\1\117" +
                    "\1\114\2\uffff\1\145\1\105\1\115\1\123\1\122\1\105\1\123\1\uffff" +
                    "\1\55\1\105\1\55\1\103\1\105\1\111\2\uffff\2\105\1\55\1\122\1\120" +
                    "\1\114\1\171\1\103\1\55\2\105\1\122\1\105\1\uffff\1\123\1\uffff" +
                    "\2\55\1\124\2\55\1\uffff\1\55\1\124\1\55\1\137\1\124\1\uffff\3\55" +
                    "\2\124\1\uffff\1\111\1\55\1\uffff\1\55\1\uffff\1\137\1\55\2\uffff" +
                    "\1\55\1\117\1\115\1\uffff\1\55\2\uffff\1\122\1\105\1\uffff\2\55" +
                    "\1\uffff";
    static final String DFA11_maxS =
            "\1\172\6\uffff\1\75\1\uffff\1\75\1\137\1\145\1\162\1\150\1\162\1" +
                    "\171\1\163\1\145\1\151\1\163\1\162\1\145\1\163\1\145\2\uffff\1\165" +
                    "\2\uffff\1\57\4\uffff\1\153\1\154\1\157\1\154\1\145\1\144\1\146" +
                    "\1\172\1\143\1\144\1\163\1\164\1\155\2\172\1\155\1\165\1\171\1\145" +
                    "\1\157\1\154\2\uffff\2\145\1\155\1\163\1\162\1\145\1\163\1\uffff" +
                    "\1\172\1\145\1\172\1\143\1\145\1\151\2\uffff\2\145\1\172\1\162\1" +
                    "\160\1\154\1\171\1\143\1\172\2\145\1\162\1\145\1\uffff\1\163\1\uffff" +
                    "\2\172\1\164\2\172\1\uffff\1\172\1\164\1\172\1\137\1\164\1\uffff" +
                    "\3\172\2\164\1\uffff\1\151\1\172\1\uffff\1\172\1\uffff\1\137\1\172" +
                    "\2\uffff\1\172\1\157\1\155\1\uffff\1\172\2\uffff\1\162\1\145\1\uffff" +
                    "\2\172\1\uffff";
    static final String DFA11_acceptS =
            "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\uffff\1\11\17\uffff\1\33\1\34" +
                    "\1\uffff\1\37\1\40\1\uffff\1\10\1\7\1\13\1\12\25\uffff\1\41\1\42" +
                    "\7\uffff\1\21\6\uffff\1\27\1\32\15\uffff\1\22\1\uffff\1\31\5\uffff" +
                    "\1\30\5\uffff\1\16\5\uffff\1\23\2\uffff\1\35\1\uffff\1\36\2\uffff" +
                    "\1\17\1\20\3\uffff\1\24\1\uffff\1\15\1\25\2\uffff\1\14\2\uffff\1" +
                    "\26";
    static final String DFA11_specialS =
            "\u0081\uffff}>";
    static final String[] DFA11_transitionS = {
            "\2\34\1\uffff\2\34\22\uffff\1\34\1\1\5\uffff\1\30\1\2\1\3\1" +
                    "\4\1\uffff\1\5\2\uffff\1\35\12\uffff\1\6\1\uffff\1\7\1\10\1" +
                    "\11\2\uffff\1\20\1\17\1\33\1\21\1\33\1\14\1\27\1\33\1\23\1\33" +
                    "\1\25\1\22\1\33\1\32\1\16\3\33\1\13\1\24\1\26\1\33\1\15\3\33" +
                    "\4\uffff\1\12\1\uffff\1\20\1\17\1\33\1\21\1\33\1\14\1\27\1\33" +
                    "\1\23\1\33\1\25\1\22\1\33\1\32\1\16\3\33\1\13\1\24\1\26\1\33" +
                    "\1\15\3\33",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\36",
            "",
            "\1\40",
            "\1\42",
            "\1\43\37\uffff\1\43",
            "\1\45\20\uffff\1\44\16\uffff\1\45\20\uffff\1\44",
            "\1\46\37\uffff\1\46",
            "\1\50\13\uffff\1\47\23\uffff\1\50\13\uffff\1\47",
            "\1\51\37\uffff\1\51",
            "\1\53\4\uffff\1\52\32\uffff\1\53\4\uffff\1\52",
            "\1\55\3\uffff\1\54\33\uffff\1\55\3\uffff\1\54",
            "\1\56\37\uffff\1\56",
            "\1\60\4\uffff\1\57\32\uffff\1\60\4\uffff\1\57",
            "\1\61\10\uffff\1\62\26\uffff\1\61\10\uffff\1\62",
            "\1\63\37\uffff\1\63",
            "\1\64\37\uffff\1\64",
            "\1\65\37\uffff\1\65",
            "",
            "",
            "\1\66\37\uffff\1\66",
            "",
            "",
            "\1\70\4\uffff\1\67",
            "",
            "",
            "",
            "",
            "\1\71",
            "\1\72\37\uffff\1\72",
            "\1\73\37\uffff\1\73",
            "\1\74\37\uffff\1\74",
            "\1\75\37\uffff\1\75",
            "\1\76\37\uffff\1\76",
            "\1\77\37\uffff\1\77",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\101\37\uffff\1\101",
            "\1\102\1\103\36\uffff\1\102\1\103",
            "\1\104\37\uffff\1\104",
            "\1\105\37\uffff\1\105",
            "\1\106\37\uffff\1\106",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\111\37\uffff\1\111",
            "\1\112\37\uffff\1\112",
            "\1\113\37\uffff\1\113",
            "\1\114\37\uffff\1\114",
            "\1\115\37\uffff\1\115",
            "\1\116\37\uffff\1\116",
            "",
            "",
            "\1\117",
            "\1\120\37\uffff\1\120",
            "\1\121\37\uffff\1\121",
            "\1\122\37\uffff\1\122",
            "\1\123\37\uffff\1\123",
            "\1\124\37\uffff\1\124",
            "\1\125\37\uffff\1\125",
            "",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\127\37\uffff\1\127",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\131\37\uffff\1\131",
            "\1\132\37\uffff\1\132",
            "\1\133\37\uffff\1\133",
            "",
            "",
            "\1\134\37\uffff\1\134",
            "\1\135\37\uffff\1\135",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\137\37\uffff\1\137",
            "\1\140\37\uffff\1\140",
            "\1\141\37\uffff\1\141",
            "\1\142",
            "\1\143\37\uffff\1\143",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\145\37\uffff\1\145",
            "\1\146\37\uffff\1\146",
            "\1\147\37\uffff\1\147",
            "\1\150\37\uffff\1\150",
            "",
            "\1\151\37\uffff\1\151",
            "",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\33\2\uffff\12\33\7\uffff\23\33\1\153\6\33\4\uffff\1\33\1" +
                    "\uffff\23\33\1\153\6\33",
            "\1\154\37\uffff\1\154",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\156\37\uffff\1\156",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\160",
            "\1\161\37\uffff\1\161",
            "",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\164\37\uffff\1\164",
            "\1\165\37\uffff\1\165",
            "",
            "\1\166\37\uffff\1\166",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "",
            "\1\170",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "",
            "",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\173\37\uffff\1\173",
            "\1\174\37\uffff\1\174",
            "",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "",
            "",
            "\1\176\37\uffff\1\176",
            "\1\177\37\uffff\1\177",
            "",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\33\2\uffff\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }

        public String getDescription() {
            return "1:1: Tokens : ( T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | SELECT | FROM | WHERE | ORDER | BY | ASC | DESC | LIMIT | OFFSET | ANCESTOR | IS | FUNCTION | AND | IN | STRING_LITERAL | DECIMAL | BOOLEAN | NULL | IDENTITY | WHITESPACE | COMMENT | MULTILINE_COMMENT );";
        }
    }


}
