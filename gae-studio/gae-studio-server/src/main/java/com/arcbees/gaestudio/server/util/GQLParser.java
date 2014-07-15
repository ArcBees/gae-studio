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

  // to prevent BitSet to be ambigious
  import java.util.List;
  import java.util.LinkedList;

  import com.google.appengine.api.datastore.Query.FilterOperator;

  import com.arcbees.gaestudio.server.util.GqlQuery.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class GQLParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "A", "ANCESTOR", "AND", "ASC", "B", "BOOLEAN", "BY", "C", "COMMENT", "D", "DECIMAL", "DESC", "DIGIT", "DOT", "E", "F", "FROM", "FUNCTION", "G", "H", "I", "IDENTITY", "IN", "IS", "J", "K", "L", "LETTER", "LIMIT", "M", "MULTILINE_COMMENT", "N", "NULL", "O", "OFFSET", "ORDER", "P", "Q", "R", "S", "SELECT", "STRING_LITERAL", "T", "U", "V", "W", "WHERE", "WHITESPACE", "X", "Y", "Z", "'!='", "'('", "')'", "'*'", "','", "':'", "'<'", "'<='", "'='", "'>'", "'>='", "'__key__'"
    };

    public static final int EOF=-1;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__59=59;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__66=66;
    public static final int A=4;
    public static final int ANCESTOR=5;
    public static final int AND=6;
    public static final int ASC=7;
    public static final int B=8;
    public static final int BOOLEAN=9;
    public static final int BY=10;
    public static final int C=11;
    public static final int COMMENT=12;
    public static final int D=13;
    public static final int DECIMAL=14;
    public static final int DESC=15;
    public static final int DIGIT=16;
    public static final int DOT=17;
    public static final int E=18;
    public static final int F=19;
    public static final int FROM=20;
    public static final int FUNCTION=21;
    public static final int G=22;
    public static final int H=23;
    public static final int I=24;
    public static final int IDENTITY=25;
    public static final int IN=26;
    public static final int IS=27;
    public static final int J=28;
    public static final int K=29;
    public static final int L=30;
    public static final int LETTER=31;
    public static final int LIMIT=32;
    public static final int M=33;
    public static final int MULTILINE_COMMENT=34;
    public static final int N=35;
    public static final int NULL=36;
    public static final int O=37;
    public static final int OFFSET=38;
    public static final int ORDER=39;
    public static final int P=40;
    public static final int Q=41;
    public static final int R=42;
    public static final int S=43;
    public static final int SELECT=44;
    public static final int STRING_LITERAL=45;
    public static final int T=46;
    public static final int U=47;
    public static final int V=48;
    public static final int W=49;
    public static final int WHERE=50;
    public static final int WHITESPACE=51;
    public static final int X=52;
    public static final int Y=53;
    public static final int Z=54;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public GQLParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public GQLParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return GQLParser.tokenNames; }
    public String getGrammarFileName() { return "GQL.g"; }


    public static class query_return extends ParserRuleReturnScope {
        public ParseResult r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "query"
    // GQL.g:35:1: query returns [ParseResult r] : select_clause ( from_clause )? ( where_clause )? ( orderby_clause )? ( limit_clause )? ( offset_clause )? ;
    public final GQLParser.query_return query() throws RecognitionException {
        GQLParser.query_return retval = new GQLParser.query_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        GQLParser.select_clause_return select_clause1 =null;

        GQLParser.from_clause_return from_clause2 =null;

        GQLParser.where_clause_return where_clause3 =null;

        GQLParser.orderby_clause_return orderby_clause4 =null;

        GQLParser.limit_clause_return limit_clause5 =null;

        GQLParser.offset_clause_return offset_clause6 =null;



        try {
            // GQL.g:35:30: ( select_clause ( from_clause )? ( where_clause )? ( orderby_clause )? ( limit_clause )? ( offset_clause )? )
            // GQL.g:36:3: select_clause ( from_clause )? ( where_clause )? ( orderby_clause )? ( limit_clause )? ( offset_clause )?
            {
            root_0 = (Object)adaptor.nil();


            retval.r = new ParseResult();

            pushFollow(FOLLOW_select_clause_in_query57);
            select_clause1=select_clause();

            state._fsp--;

            adaptor.addChild(root_0, select_clause1.getTree());

            retval.r.setSelect((select_clause1!=null?select_clause1.r:null));

            // GQL.g:38:3: ( from_clause )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==FROM) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // GQL.g:38:4: from_clause
                    {
                    pushFollow(FOLLOW_from_clause_in_query64);
                    from_clause2=from_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, from_clause2.getTree());

                    retval.r.setFrom((from_clause2!=null?from_clause2.r:null));

                    }
                    break;

            }


            // GQL.g:39:3: ( where_clause )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==WHERE) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // GQL.g:39:4: where_clause
                    {
                    pushFollow(FOLLOW_where_clause_in_query74);
                    where_clause3=where_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, where_clause3.getTree());

                    retval.r.setWhere((where_clause3!=null?where_clause3.r:null));

                    }
                    break;

            }


            // GQL.g:40:3: ( orderby_clause )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==ORDER) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // GQL.g:40:4: orderby_clause
                    {
                    pushFollow(FOLLOW_orderby_clause_in_query84);
                    orderby_clause4=orderby_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, orderby_clause4.getTree());

                    retval.r.setOrderBy((orderby_clause4!=null?orderby_clause4.r:null));

                    }
                    break;

            }


            // GQL.g:41:3: ( limit_clause )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LIMIT) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // GQL.g:41:4: limit_clause
                    {
                    pushFollow(FOLLOW_limit_clause_in_query93);
                    limit_clause5=limit_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, limit_clause5.getTree());

                    retval.r.setLimit((limit_clause5!=null?limit_clause5.r:null));

                    }
                    break;

            }


            // GQL.g:42:3: ( offset_clause )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==OFFSET) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // GQL.g:42:4: offset_clause
                    {
                    pushFollow(FOLLOW_offset_clause_in_query102);
                    offset_clause6=offset_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, offset_clause6.getTree());

                    retval.r.setOffset((offset_clause6!=null?offset_clause6.r:null));

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "query"


    public static class select_clause_return extends ParserRuleReturnScope {
        public Select r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "select_clause"
    // GQL.g:45:1: select_clause returns [Select r] : SELECT ( '*' | '__key__' | (i1= IDENTITY ) ( ',' i2= IDENTITY )* ) ;
    public final GQLParser.select_clause_return select_clause() throws RecognitionException {
        GQLParser.select_clause_return retval = new GQLParser.select_clause_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token i1=null;
        Token i2=null;
        Token SELECT7=null;
        Token char_literal8=null;
        Token string_literal9=null;
        Token char_literal10=null;

        Object i1_tree=null;
        Object i2_tree=null;
        Object SELECT7_tree=null;
        Object char_literal8_tree=null;
        Object string_literal9_tree=null;
        Object char_literal10_tree=null;

        try {
            // GQL.g:45:33: ( SELECT ( '*' | '__key__' | (i1= IDENTITY ) ( ',' i2= IDENTITY )* ) )
            // GQL.g:46:3: SELECT ( '*' | '__key__' | (i1= IDENTITY ) ( ',' i2= IDENTITY )* )
            {
            root_0 = (Object)adaptor.nil();


            SELECT7=(Token)match(input,SELECT,FOLLOW_SELECT_in_select_clause122);
            SELECT7_tree =
            (Object)adaptor.create(SELECT7)
            ;
            adaptor.addChild(root_0, SELECT7_tree);


            // GQL.g:47:3: ( '*' | '__key__' | (i1= IDENTITY ) ( ',' i2= IDENTITY )* )
            int alt7=3;
            switch ( input.LA(1) ) {
            case 58:
                {
                alt7=1;
                }
                break;
            case 66:
                {
                alt7=2;
                }
                break;
            case IDENTITY:
                {
                alt7=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }

            switch (alt7) {
                case 1 :
                    // GQL.g:47:4: '*'
                    {
                    char_literal8=(Token)match(input,58,FOLLOW_58_in_select_clause127);
                    char_literal8_tree =
                    (Object)adaptor.create(char_literal8)
                    ;
                    adaptor.addChild(root_0, char_literal8_tree);


                    retval.r = new Select(false);

                    }
                    break;
                case 2 :
                    // GQL.g:48:5: '__key__'
                    {
                    string_literal9=(Token)match(input,66,FOLLOW_66_in_select_clause135);
                    string_literal9_tree =
                    (Object)adaptor.create(string_literal9)
                    ;
                    adaptor.addChild(root_0, string_literal9_tree);


                    retval.r = new Select(true);

                    }
                    break;
                case 3 :
                    // GQL.g:49:5: (i1= IDENTITY ) ( ',' i2= IDENTITY )*
                    {
                    // GQL.g:49:5: (i1= IDENTITY )
                    // GQL.g:49:6: i1= IDENTITY
                    {
                    i1=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_select_clause146);
                    i1_tree =
                    (Object)adaptor.create(i1)
                    ;
                    adaptor.addChild(root_0, i1_tree);


                    retval.r = new Select(false);  retval.r.addProjection((i1!=null?i1.getText():null) );

                    }


                    // GQL.g:49:75: ( ',' i2= IDENTITY )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==59) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // GQL.g:49:76: ',' i2= IDENTITY
                    	    {
                    	    char_literal10=(Token)match(input,59,FOLLOW_59_in_select_clause152);
                    	    char_literal10_tree =
                    	    (Object)adaptor.create(char_literal10)
                    	    ;
                    	    adaptor.addChild(root_0, char_literal10_tree);


                    	    i2=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_select_clause156);
                    	    i2_tree =
                    	    (Object)adaptor.create(i2)
                    	    ;
                    	    adaptor.addChild(root_0, i2_tree);


                    	     retval.r.addProjection((i2!=null?i2.getText():null) );

                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "select_clause"


    public static class from_clause_return extends ParserRuleReturnScope {
        public From r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "from_clause"
    // GQL.g:55:1: from_clause returns [From r] : FROM IDENTITY ;
    public final GQLParser.from_clause_return from_clause() throws RecognitionException {
        GQLParser.from_clause_return retval = new GQLParser.from_clause_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token FROM11=null;
        Token IDENTITY12=null;

        Object FROM11_tree=null;
        Object IDENTITY12_tree=null;

        try {
            // GQL.g:55:29: ( FROM IDENTITY )
            // GQL.g:56:3: FROM IDENTITY
            {
            root_0 = (Object)adaptor.nil();


            FROM11=(Token)match(input,FROM,FOLLOW_FROM_in_from_clause178);
            FROM11_tree =
            (Object)adaptor.create(FROM11)
            ;
            adaptor.addChild(root_0, FROM11_tree);


            IDENTITY12=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_from_clause180);
            IDENTITY12_tree =
            (Object)adaptor.create(IDENTITY12)
            ;
            adaptor.addChild(root_0, IDENTITY12_tree);


            retval.r = new From((IDENTITY12!=null?IDENTITY12.getText():null));

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "from_clause"


    public static class where_clause_return extends ParserRuleReturnScope {
        public Where r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "where_clause"
    // GQL.g:60:1: where_clause returns [Where r] : WHERE (c1= condition |a1= ancestorCondition ) ( AND (c2= condition |a2= ancestorCondition ) )* ;
    public final GQLParser.where_clause_return where_clause() throws RecognitionException {
        GQLParser.where_clause_return retval = new GQLParser.where_clause_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token WHERE13=null;
        Token AND14=null;
        GQLParser.condition_return c1 =null;

        GQLParser.ancestorCondition_return a1 =null;

        GQLParser.condition_return c2 =null;

        GQLParser.ancestorCondition_return a2 =null;


        Object WHERE13_tree=null;
        Object AND14_tree=null;

        try {
            // GQL.g:60:31: ( WHERE (c1= condition |a1= ancestorCondition ) ( AND (c2= condition |a2= ancestorCondition ) )* )
            // GQL.g:61:3: WHERE (c1= condition |a1= ancestorCondition ) ( AND (c2= condition |a2= ancestorCondition ) )*
            {
            root_0 = (Object)adaptor.nil();


            retval.r = new Where();

            WHERE13=(Token)match(input,WHERE,FOLLOW_WHERE_in_where_clause201);
            WHERE13_tree =
            (Object)adaptor.create(WHERE13)
            ;
            adaptor.addChild(root_0, WHERE13_tree);


            // GQL.g:62:9: (c1= condition |a1= ancestorCondition )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==IDENTITY) ) {
                alt8=1;
            }
            else if ( (LA8_0==ANCESTOR) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }
            switch (alt8) {
                case 1 :
                    // GQL.g:62:10: c1= condition
                    {
                    pushFollow(FOLLOW_condition_in_where_clause206);
                    c1=condition();

                    state._fsp--;

                    adaptor.addChild(root_0, c1.getTree());

                    retval.r.withCondition(c1.r);

                    }
                    break;
                case 2 :
                    // GQL.g:62:51: a1= ancestorCondition
                    {
                    pushFollow(FOLLOW_ancestorCondition_in_where_clause214);
                    a1=ancestorCondition();

                    state._fsp--;

                    adaptor.addChild(root_0, a1.getTree());

                    retval.r.withAncestor(a1.r);

                    }
                    break;

            }


            // GQL.g:63:5: ( AND (c2= condition |a2= ancestorCondition ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==AND) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // GQL.g:63:6: AND (c2= condition |a2= ancestorCondition )
            	    {
            	    AND14=(Token)match(input,AND,FOLLOW_AND_in_where_clause224);
            	    AND14_tree =
            	    (Object)adaptor.create(AND14)
            	    ;
            	    adaptor.addChild(root_0, AND14_tree);


            	    // GQL.g:63:10: (c2= condition |a2= ancestorCondition )
            	    int alt9=2;
            	    int LA9_0 = input.LA(1);

            	    if ( (LA9_0==IDENTITY) ) {
            	        alt9=1;
            	    }
            	    else if ( (LA9_0==ANCESTOR) ) {
            	        alt9=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 9, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt9) {
            	        case 1 :
            	            // GQL.g:63:11: c2= condition
            	            {
            	            pushFollow(FOLLOW_condition_in_where_clause229);
            	            c2=condition();

            	            state._fsp--;

            	            adaptor.addChild(root_0, c2.getTree());

            	            retval.r.withCondition(c2.r);

            	            }
            	            break;
            	        case 2 :
            	            // GQL.g:63:52: a2= ancestorCondition
            	            {
            	            pushFollow(FOLLOW_ancestorCondition_in_where_clause237);
            	            a2=ancestorCondition();

            	            state._fsp--;

            	            adaptor.addChild(root_0, a2.getTree());

            	            retval.r.withAncestor(a2.r);

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "where_clause"


    public static class orderby_clause_return extends ParserRuleReturnScope {
        public OrderBy r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "orderby_clause"
    // GQL.g:67:1: orderby_clause returns [OrderBy r] : ORDER BY i1= IDENTITY ( ASC |d1= DESC )? ( ',' i2= IDENTITY ( ASC |d2= DESC )? )* ;
    public final GQLParser.orderby_clause_return orderby_clause() throws RecognitionException {
        GQLParser.orderby_clause_return retval = new GQLParser.orderby_clause_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token i1=null;
        Token d1=null;
        Token i2=null;
        Token d2=null;
        Token ORDER15=null;
        Token BY16=null;
        Token ASC17=null;
        Token char_literal18=null;
        Token ASC19=null;

        Object i1_tree=null;
        Object d1_tree=null;
        Object i2_tree=null;
        Object d2_tree=null;
        Object ORDER15_tree=null;
        Object BY16_tree=null;
        Object ASC17_tree=null;
        Object char_literal18_tree=null;
        Object ASC19_tree=null;

        try {
            // GQL.g:67:35: ( ORDER BY i1= IDENTITY ( ASC |d1= DESC )? ( ',' i2= IDENTITY ( ASC |d2= DESC )? )* )
            // GQL.g:68:3: ORDER BY i1= IDENTITY ( ASC |d1= DESC )? ( ',' i2= IDENTITY ( ASC |d2= DESC )? )*
            {
            root_0 = (Object)adaptor.nil();


            retval.r = new OrderBy();

            ORDER15=(Token)match(input,ORDER,FOLLOW_ORDER_in_orderby_clause263);
            ORDER15_tree =
            (Object)adaptor.create(ORDER15)
            ;
            adaptor.addChild(root_0, ORDER15_tree);


            BY16=(Token)match(input,BY,FOLLOW_BY_in_orderby_clause265);
            BY16_tree =
            (Object)adaptor.create(BY16)
            ;
            adaptor.addChild(root_0, BY16_tree);


            i1=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_orderby_clause269);
            i1_tree =
            (Object)adaptor.create(i1)
            ;
            adaptor.addChild(root_0, i1_tree);


            OrderByItem it = new OrderByItem((i1!=null?i1.getText():null));

            // GQL.g:69:71: ( ASC |d1= DESC )?
            int alt11=3;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ASC) ) {
                alt11=1;
            }
            else if ( (LA11_0==DESC) ) {
                alt11=2;
            }
            switch (alt11) {
                case 1 :
                    // GQL.g:69:72: ASC
                    {
                    ASC17=(Token)match(input,ASC,FOLLOW_ASC_in_orderby_clause275);
                    ASC17_tree =
                    (Object)adaptor.create(ASC17)
                    ;
                    adaptor.addChild(root_0, ASC17_tree);


                    }
                    break;
                case 2 :
                    // GQL.g:69:78: d1= DESC
                    {
                    d1=(Token)match(input,DESC,FOLLOW_DESC_in_orderby_clause281);
                    d1_tree =
                    (Object)adaptor.create(d1)
                    ;
                    adaptor.addChild(root_0, d1_tree);


                    it.setDirection(false);

                    }
                    break;

            }


            retval.r.withItem(it);

            // GQL.g:70:3: ( ',' i2= IDENTITY ( ASC |d2= DESC )? )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==59) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // GQL.g:70:4: ',' i2= IDENTITY ( ASC |d2= DESC )?
            	    {
            	    char_literal18=(Token)match(input,59,FOLLOW_59_in_orderby_clause293);
            	    char_literal18_tree =
            	    (Object)adaptor.create(char_literal18)
            	    ;
            	    adaptor.addChild(root_0, char_literal18_tree);


            	    i2=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_orderby_clause297);
            	    i2_tree =
            	    (Object)adaptor.create(i2)
            	    ;
            	    adaptor.addChild(root_0, i2_tree);


            	    OrderByItem it2 = new OrderByItem((i2!=null?i2.getText():null));

            	    // GQL.g:70:67: ( ASC |d2= DESC )?
            	    int alt12=3;
            	    int LA12_0 = input.LA(1);

            	    if ( (LA12_0==ASC) ) {
            	        alt12=1;
            	    }
            	    else if ( (LA12_0==DESC) ) {
            	        alt12=2;
            	    }
            	    switch (alt12) {
            	        case 1 :
            	            // GQL.g:70:68: ASC
            	            {
            	            ASC19=(Token)match(input,ASC,FOLLOW_ASC_in_orderby_clause302);
            	            ASC19_tree =
            	            (Object)adaptor.create(ASC19)
            	            ;
            	            adaptor.addChild(root_0, ASC19_tree);


            	            }
            	            break;
            	        case 2 :
            	            // GQL.g:70:74: d2= DESC
            	            {
            	            d2=(Token)match(input,DESC,FOLLOW_DESC_in_orderby_clause308);
            	            d2_tree =
            	            (Object)adaptor.create(d2)
            	            ;
            	            adaptor.addChild(root_0, d2_tree);


            	            it2.setDirection(false);

            	            }
            	            break;

            	    }


            	    retval.r.withItem(it2);

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "orderby_clause"


    public static class limit_clause_return extends ParserRuleReturnScope {
        public Limit r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "limit_clause"
    // GQL.g:74:1: limit_clause returns [Limit r] : LIMIT DECIMAL ;
    public final GQLParser.limit_clause_return limit_clause() throws RecognitionException {
        GQLParser.limit_clause_return retval = new GQLParser.limit_clause_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token LIMIT20=null;
        Token DECIMAL21=null;

        Object LIMIT20_tree=null;
        Object DECIMAL21_tree=null;

        try {
            // GQL.g:74:31: ( LIMIT DECIMAL )
            // GQL.g:75:3: LIMIT DECIMAL
            {
            root_0 = (Object)adaptor.nil();


            LIMIT20=(Token)match(input,LIMIT,FOLLOW_LIMIT_in_limit_clause333);
            LIMIT20_tree =
            (Object)adaptor.create(LIMIT20)
            ;
            adaptor.addChild(root_0, LIMIT20_tree);


            DECIMAL21=(Token)match(input,DECIMAL,FOLLOW_DECIMAL_in_limit_clause335);
            DECIMAL21_tree =
            (Object)adaptor.create(DECIMAL21)
            ;
            adaptor.addChild(root_0, DECIMAL21_tree);


            retval.r = new Limit(Integer.valueOf((DECIMAL21!=null?DECIMAL21.getText():null)));

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "limit_clause"


    public static class offset_clause_return extends ParserRuleReturnScope {
        public Offset r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "offset_clause"
    // GQL.g:79:1: offset_clause returns [Offset r] : OFFSET DECIMAL ;
    public final GQLParser.offset_clause_return offset_clause() throws RecognitionException {
        GQLParser.offset_clause_return retval = new GQLParser.offset_clause_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token OFFSET22=null;
        Token DECIMAL23=null;

        Object OFFSET22_tree=null;
        Object DECIMAL23_tree=null;

        try {
            // GQL.g:79:33: ( OFFSET DECIMAL )
            // GQL.g:80:3: OFFSET DECIMAL
            {
            root_0 = (Object)adaptor.nil();


            OFFSET22=(Token)match(input,OFFSET,FOLLOW_OFFSET_in_offset_clause352);
            OFFSET22_tree =
            (Object)adaptor.create(OFFSET22)
            ;
            adaptor.addChild(root_0, OFFSET22_tree);


            DECIMAL23=(Token)match(input,DECIMAL,FOLLOW_DECIMAL_in_offset_clause354);
            DECIMAL23_tree =
            (Object)adaptor.create(DECIMAL23)
            ;
            adaptor.addChild(root_0, DECIMAL23_tree);


            retval.r = new Offset(Integer.valueOf((DECIMAL23!=null?DECIMAL23.getText():null)));

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "offset_clause"


    public static class condition_return extends ParserRuleReturnScope {
        public Condition r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "condition"
    // GQL.g:83:1: condition returns [Condition r] : ( (i= IDENTITY '<' v= value |i= IDENTITY '<=' v= value |i= IDENTITY '>' v= value |i= IDENTITY '>=' v= value |i= IDENTITY '=' v= value |i= IDENTITY '!=' v= value ) | ( IDENTITY IN list ) );
    public final GQLParser.condition_return condition() throws RecognitionException {
        GQLParser.condition_return retval = new GQLParser.condition_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token i=null;
        Token char_literal24=null;
        Token string_literal25=null;
        Token char_literal26=null;
        Token string_literal27=null;
        Token char_literal28=null;
        Token string_literal29=null;
        Token IDENTITY30=null;
        Token IN31=null;
        GQLParser.value_return v =null;

        GQLParser.list_return list32 =null;


        Object i_tree=null;
        Object char_literal24_tree=null;
        Object string_literal25_tree=null;
        Object char_literal26_tree=null;
        Object string_literal27_tree=null;
        Object char_literal28_tree=null;
        Object string_literal29_tree=null;
        Object IDENTITY30_tree=null;
        Object IN31_tree=null;

        try {
            // GQL.g:84:3: ( (i= IDENTITY '<' v= value |i= IDENTITY '<=' v= value |i= IDENTITY '>' v= value |i= IDENTITY '>=' v= value |i= IDENTITY '=' v= value |i= IDENTITY '!=' v= value ) | ( IDENTITY IN list ) )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==IDENTITY) ) {
                int LA15_1 = input.LA(2);

                if ( (LA15_1==55||(LA15_1 >= 61 && LA15_1 <= 65)) ) {
                    alt15=1;
                }
                else if ( (LA15_1==IN) ) {
                    alt15=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }
            switch (alt15) {
                case 1 :
                    // GQL.g:85:3: (i= IDENTITY '<' v= value |i= IDENTITY '<=' v= value |i= IDENTITY '>' v= value |i= IDENTITY '>=' v= value |i= IDENTITY '=' v= value |i= IDENTITY '!=' v= value )
                    {
                    root_0 = (Object)adaptor.nil();


                    // GQL.g:85:3: (i= IDENTITY '<' v= value |i= IDENTITY '<=' v= value |i= IDENTITY '>' v= value |i= IDENTITY '>=' v= value |i= IDENTITY '=' v= value |i= IDENTITY '!=' v= value )
                    int alt14=6;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==IDENTITY) ) {
                        switch ( input.LA(2) ) {
                        case 61:
                            {
                            alt14=1;
                            }
                            break;
                        case 62:
                            {
                            alt14=2;
                            }
                            break;
                        case 64:
                            {
                            alt14=3;
                            }
                            break;
                        case 65:
                            {
                            alt14=4;
                            }
                            break;
                        case 63:
                            {
                            alt14=5;
                            }
                            break;
                        case 55:
                            {
                            alt14=6;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 14, 1, input);

                            throw nvae;

                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 14, 0, input);

                        throw nvae;

                    }
                    switch (alt14) {
                        case 1 :
                            // GQL.g:85:4: i= IDENTITY '<' v= value
                            {
                            i=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_condition377);
                            i_tree =
                            (Object)adaptor.create(i)
                            ;
                            adaptor.addChild(root_0, i_tree);


                            char_literal24=(Token)match(input,61,FOLLOW_61_in_condition379);
                            char_literal24_tree =
                            (Object)adaptor.create(char_literal24)
                            ;
                            adaptor.addChild(root_0, char_literal24_tree);


                            pushFollow(FOLLOW_value_in_condition383);
                            v=value();

                            state._fsp--;

                            adaptor.addChild(root_0, v.getTree());

                            retval.r = new Condition((i!=null?i.getText():null), FilterOperator.LESS_THAN, (v!=null?v.r:null));

                            }
                            break;
                        case 2 :
                            // GQL.g:86:4: i= IDENTITY '<=' v= value
                            {
                            i=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_condition395);
                            i_tree =
                            (Object)adaptor.create(i)
                            ;
                            adaptor.addChild(root_0, i_tree);


                            string_literal25=(Token)match(input,62,FOLLOW_62_in_condition397);
                            string_literal25_tree =
                            (Object)adaptor.create(string_literal25)
                            ;
                            adaptor.addChild(root_0, string_literal25_tree);


                            pushFollow(FOLLOW_value_in_condition401);
                            v=value();

                            state._fsp--;

                            adaptor.addChild(root_0, v.getTree());

                            retval.r = new Condition((i!=null?i.getText():null), FilterOperator.LESS_THAN_OR_EQUAL, (v!=null?v.r:null));

                            }
                            break;
                        case 3 :
                            // GQL.g:87:4: i= IDENTITY '>' v= value
                            {
                            i=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_condition411);
                            i_tree =
                            (Object)adaptor.create(i)
                            ;
                            adaptor.addChild(root_0, i_tree);


                            char_literal26=(Token)match(input,64,FOLLOW_64_in_condition413);
                            char_literal26_tree =
                            (Object)adaptor.create(char_literal26)
                            ;
                            adaptor.addChild(root_0, char_literal26_tree);


                            pushFollow(FOLLOW_value_in_condition417);
                            v=value();

                            state._fsp--;

                            adaptor.addChild(root_0, v.getTree());

                            retval.r = new Condition((i!=null?i.getText():null), FilterOperator.GREATER_THAN, (v!=null?v.r:null));

                            }
                            break;
                        case 4 :
                            // GQL.g:88:4: i= IDENTITY '>=' v= value
                            {
                            i=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_condition428);
                            i_tree =
                            (Object)adaptor.create(i)
                            ;
                            adaptor.addChild(root_0, i_tree);


                            string_literal27=(Token)match(input,65,FOLLOW_65_in_condition430);
                            string_literal27_tree =
                            (Object)adaptor.create(string_literal27)
                            ;
                            adaptor.addChild(root_0, string_literal27_tree);


                            pushFollow(FOLLOW_value_in_condition434);
                            v=value();

                            state._fsp--;

                            adaptor.addChild(root_0, v.getTree());

                            retval.r = new Condition((i!=null?i.getText():null), FilterOperator.GREATER_THAN_OR_EQUAL, (v!=null?v.r:null));

                            }
                            break;
                        case 5 :
                            // GQL.g:89:4: i= IDENTITY '=' v= value
                            {
                            i=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_condition444);
                            i_tree =
                            (Object)adaptor.create(i)
                            ;
                            adaptor.addChild(root_0, i_tree);


                            char_literal28=(Token)match(input,63,FOLLOW_63_in_condition446);
                            char_literal28_tree =
                            (Object)adaptor.create(char_literal28)
                            ;
                            adaptor.addChild(root_0, char_literal28_tree);


                            pushFollow(FOLLOW_value_in_condition450);
                            v=value();

                            state._fsp--;

                            adaptor.addChild(root_0, v.getTree());

                            retval.r = new Condition((i!=null?i.getText():null), FilterOperator.EQUAL, (v!=null?v.r:null));

                            }
                            break;
                        case 6 :
                            // GQL.g:90:4: i= IDENTITY '!=' v= value
                            {
                            i=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_condition461);
                            i_tree =
                            (Object)adaptor.create(i)
                            ;
                            adaptor.addChild(root_0, i_tree);


                            string_literal29=(Token)match(input,55,FOLLOW_55_in_condition463);
                            string_literal29_tree =
                            (Object)adaptor.create(string_literal29)
                            ;
                            adaptor.addChild(root_0, string_literal29_tree);


                            pushFollow(FOLLOW_value_in_condition467);
                            v=value();

                            state._fsp--;

                            adaptor.addChild(root_0, v.getTree());

                            retval.r = new Condition((i!=null?i.getText():null), FilterOperator.NOT_EQUAL, (v!=null?v.r:null));

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // GQL.g:93:3: ( IDENTITY IN list )
                    {
                    root_0 = (Object)adaptor.nil();


                    // GQL.g:93:3: ( IDENTITY IN list )
                    // GQL.g:93:5: IDENTITY IN list
                    {
                    IDENTITY30=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_condition485);
                    IDENTITY30_tree =
                    (Object)adaptor.create(IDENTITY30)
                    ;
                    adaptor.addChild(root_0, IDENTITY30_tree);


                    IN31=(Token)match(input,IN,FOLLOW_IN_in_condition487);
                    IN31_tree =
                    (Object)adaptor.create(IN31)
                    ;
                    adaptor.addChild(root_0, IN31_tree);


                    pushFollow(FOLLOW_list_in_condition489);
                    list32=list();

                    state._fsp--;

                    adaptor.addChild(root_0, list32.getTree());

                    retval.r = new Condition((IDENTITY30!=null?IDENTITY30.getText():null), FilterOperator.IN, new ListEvaluator((list32!=null?list32.r:null)));

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "condition"


    public static class ancestorCondition_return extends ParserRuleReturnScope {
        public Evaluator r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "ancestorCondition"
    // GQL.g:97:1: ancestorCondition returns [Evaluator r] : ANCESTOR IS value ;
    public final GQLParser.ancestorCondition_return ancestorCondition() throws RecognitionException {
        GQLParser.ancestorCondition_return retval = new GQLParser.ancestorCondition_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ANCESTOR33=null;
        Token IS34=null;
        GQLParser.value_return value35 =null;


        Object ANCESTOR33_tree=null;
        Object IS34_tree=null;

        try {
            // GQL.g:97:40: ( ANCESTOR IS value )
            // GQL.g:98:3: ANCESTOR IS value
            {
            root_0 = (Object)adaptor.nil();


            ANCESTOR33=(Token)match(input,ANCESTOR,FOLLOW_ANCESTOR_in_ancestorCondition509);
            ANCESTOR33_tree =
            (Object)adaptor.create(ANCESTOR33)
            ;
            adaptor.addChild(root_0, ANCESTOR33_tree);


            IS34=(Token)match(input,IS,FOLLOW_IS_in_ancestorCondition511);
            IS34_tree =
            (Object)adaptor.create(IS34)
            ;
            adaptor.addChild(root_0, IS34_tree);


            pushFollow(FOLLOW_value_in_ancestorCondition513);
            value35=value();

            state._fsp--;

            adaptor.addChild(root_0, value35.getTree());

            retval.r = (value35!=null?value35.r:null);

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "ancestorCondition"


    public static class value_return extends ParserRuleReturnScope {
        public Evaluator r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "value"
    // GQL.g:101:1: value returns [Evaluator r] : ( NULL |d= DECIMAL | STRING_LITERAL | BOOLEAN | ':' IDENTITY | ':' d= DECIMAL | FUNCTION list );
    public final GQLParser.value_return value() throws RecognitionException {
        GQLParser.value_return retval = new GQLParser.value_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token d=null;
        Token NULL36=null;
        Token STRING_LITERAL37=null;
        Token BOOLEAN38=null;
        Token char_literal39=null;
        Token IDENTITY40=null;
        Token char_literal41=null;
        Token FUNCTION42=null;
        GQLParser.list_return list43 =null;


        Object d_tree=null;
        Object NULL36_tree=null;
        Object STRING_LITERAL37_tree=null;
        Object BOOLEAN38_tree=null;
        Object char_literal39_tree=null;
        Object IDENTITY40_tree=null;
        Object char_literal41_tree=null;
        Object FUNCTION42_tree=null;

        try {
            // GQL.g:102:3: ( NULL |d= DECIMAL | STRING_LITERAL | BOOLEAN | ':' IDENTITY | ':' d= DECIMAL | FUNCTION list )
            int alt16=7;
            switch ( input.LA(1) ) {
            case NULL:
                {
                alt16=1;
                }
                break;
            case DECIMAL:
                {
                alt16=2;
                }
                break;
            case STRING_LITERAL:
                {
                alt16=3;
                }
                break;
            case BOOLEAN:
                {
                alt16=4;
                }
                break;
            case 60:
                {
                int LA16_5 = input.LA(2);

                if ( (LA16_5==IDENTITY) ) {
                    alt16=5;
                }
                else if ( (LA16_5==DECIMAL) ) {
                    alt16=6;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 5, input);

                    throw nvae;

                }
                }
                break;
            case FUNCTION:
                {
                alt16=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }

            switch (alt16) {
                case 1 :
                    // GQL.g:102:5: NULL
                    {
                    root_0 = (Object)adaptor.nil();


                    NULL36=(Token)match(input,NULL,FOLLOW_NULL_in_value530);
                    NULL36_tree =
                    (Object)adaptor.create(NULL36)
                    ;
                    adaptor.addChild(root_0, NULL36_tree);


                    retval.r = NullEvaluator.get();

                    }
                    break;
                case 2 :
                    // GQL.g:103:5: d= DECIMAL
                    {
                    root_0 = (Object)adaptor.nil();


                    d=(Token)match(input,DECIMAL,FOLLOW_DECIMAL_in_value541);
                    d_tree =
                    (Object)adaptor.create(d)
                    ;
                    adaptor.addChild(root_0, d_tree);


                    retval.r = new DecimalEvaluator((d!=null?d.getText():null));

                    }
                    break;
                case 3 :
                    // GQL.g:104:5: STRING_LITERAL
                    {
                    root_0 = (Object)adaptor.nil();


                    STRING_LITERAL37=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_value549);
                    STRING_LITERAL37_tree =
                    (Object)adaptor.create(STRING_LITERAL37)
                    ;
                    adaptor.addChild(root_0, STRING_LITERAL37_tree);


                    retval.r = new StringEvaluator((STRING_LITERAL37!=null?STRING_LITERAL37.getText():null));

                    }
                    break;
                case 4 :
                    // GQL.g:105:5: BOOLEAN
                    {
                    root_0 = (Object)adaptor.nil();


                    BOOLEAN38=(Token)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_value557);
                    BOOLEAN38_tree =
                    (Object)adaptor.create(BOOLEAN38)
                    ;
                    adaptor.addChild(root_0, BOOLEAN38_tree);


                    retval.r = new BooleanEvaluator((BOOLEAN38!=null?BOOLEAN38.getText():null));

                    }
                    break;
                case 5 :
                    // GQL.g:106:5: ':' IDENTITY
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal39=(Token)match(input,60,FOLLOW_60_in_value565);
                    char_literal39_tree =
                    (Object)adaptor.create(char_literal39)
                    ;
                    adaptor.addChild(root_0, char_literal39_tree);


                    IDENTITY40=(Token)match(input,IDENTITY,FOLLOW_IDENTITY_in_value567);
                    IDENTITY40_tree =
                    (Object)adaptor.create(IDENTITY40)
                    ;
                    adaptor.addChild(root_0, IDENTITY40_tree);


                    retval.r = new ParamEvaluator((IDENTITY40!=null?IDENTITY40.getText():null));

                    }
                    break;
                case 6 :
                    // GQL.g:107:5: ':' d= DECIMAL
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal41=(Token)match(input,60,FOLLOW_60_in_value575);
                    char_literal41_tree =
                    (Object)adaptor.create(char_literal41)
                    ;
                    adaptor.addChild(root_0, char_literal41_tree);


                    d=(Token)match(input,DECIMAL,FOLLOW_DECIMAL_in_value579);
                    d_tree =
                    (Object)adaptor.create(d)
                    ;
                    adaptor.addChild(root_0, d_tree);


                    retval.r = new ParamEvaluator((d!=null?d.getText():null));

                    }
                    break;
                case 7 :
                    // GQL.g:108:5: FUNCTION list
                    {
                    root_0 = (Object)adaptor.nil();


                    FUNCTION42=(Token)match(input,FUNCTION,FOLLOW_FUNCTION_in_value587);
                    FUNCTION42_tree =
                    (Object)adaptor.create(FUNCTION42)
                    ;
                    adaptor.addChild(root_0, FUNCTION42_tree);


                    pushFollow(FOLLOW_list_in_value589);
                    list43=list();

                    state._fsp--;

                    adaptor.addChild(root_0, list43.getTree());

                    retval.r = new FunctionEvaluator((FUNCTION42!=null?FUNCTION42.getText():null), (list43!=null?list43.r:null));

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "value"


    public static class list_return extends ParserRuleReturnScope {
        public List r;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "list"
    // GQL.g:112:1: list returns [List r] : '(' v1= value ( ',' v2= value )* ')' ;
    public final GQLParser.list_return list() throws RecognitionException {
        GQLParser.list_return retval = new GQLParser.list_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal44=null;
        Token char_literal45=null;
        Token char_literal46=null;
        GQLParser.value_return v1 =null;

        GQLParser.value_return v2 =null;


        Object char_literal44_tree=null;
        Object char_literal45_tree=null;
        Object char_literal46_tree=null;

        try {
            // GQL.g:112:23: ( '(' v1= value ( ',' v2= value )* ')' )
            // GQL.g:113:3: '(' v1= value ( ',' v2= value )* ')'
            {
            root_0 = (Object)adaptor.nil();


            retval.r = new LinkedList<Evaluator>();

            char_literal44=(Token)match(input,56,FOLLOW_56_in_list611);
            char_literal44_tree =
            (Object)adaptor.create(char_literal44)
            ;
            adaptor.addChild(root_0, char_literal44_tree);


            pushFollow(FOLLOW_value_in_list615);
            v1=value();

            state._fsp--;

            adaptor.addChild(root_0, v1.getTree());

            retval.r.add((v1!=null?v1.r:null));

            // GQL.g:114:33: ( ',' v2= value )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==59) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // GQL.g:114:34: ',' v2= value
            	    {
            	    char_literal45=(Token)match(input,59,FOLLOW_59_in_list620);
            	    char_literal45_tree =
            	    (Object)adaptor.create(char_literal45)
            	    ;
            	    adaptor.addChild(root_0, char_literal45_tree);


            	    pushFollow(FOLLOW_value_in_list624);
            	    v2=value();

            	    state._fsp--;

            	    adaptor.addChild(root_0, v2.getTree());

            	    retval.r.add((v2!=null?v2.r:null));

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            char_literal46=(Token)match(input,57,FOLLOW_57_in_list631);
            char_literal46_tree =
            (Object)adaptor.create(char_literal46)
            ;
            adaptor.addChild(root_0, char_literal46_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "list"

    // Delegated rules




    public static final BitSet FOLLOW_select_clause_in_query57 = new BitSet(new long[]{0x000400C100100002L});
    public static final BitSet FOLLOW_from_clause_in_query64 = new BitSet(new long[]{0x000400C100000002L});
    public static final BitSet FOLLOW_where_clause_in_query74 = new BitSet(new long[]{0x000000C100000002L});
    public static final BitSet FOLLOW_orderby_clause_in_query84 = new BitSet(new long[]{0x0000004100000002L});
    public static final BitSet FOLLOW_limit_clause_in_query93 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_offset_clause_in_query102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECT_in_select_clause122 = new BitSet(new long[]{0x0400000002000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_58_in_select_clause127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_select_clause135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTITY_in_select_clause146 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_select_clause152 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_IDENTITY_in_select_clause156 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_FROM_in_from_clause178 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_IDENTITY_in_from_clause180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHERE_in_where_clause201 = new BitSet(new long[]{0x0000000002000020L});
    public static final BitSet FOLLOW_condition_in_where_clause206 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_ancestorCondition_in_where_clause214 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_AND_in_where_clause224 = new BitSet(new long[]{0x0000000002000020L});
    public static final BitSet FOLLOW_condition_in_where_clause229 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_ancestorCondition_in_where_clause237 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_ORDER_in_orderby_clause263 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_BY_in_orderby_clause265 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_IDENTITY_in_orderby_clause269 = new BitSet(new long[]{0x0800000000008082L});
    public static final BitSet FOLLOW_ASC_in_orderby_clause275 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_DESC_in_orderby_clause281 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_orderby_clause293 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_IDENTITY_in_orderby_clause297 = new BitSet(new long[]{0x0800000000008082L});
    public static final BitSet FOLLOW_ASC_in_orderby_clause302 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_DESC_in_orderby_clause308 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_LIMIT_in_limit_clause333 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DECIMAL_in_limit_clause335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OFFSET_in_offset_clause352 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DECIMAL_in_offset_clause354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTITY_in_condition377 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_condition379 = new BitSet(new long[]{0x1000201000204200L});
    public static final BitSet FOLLOW_value_in_condition383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTITY_in_condition395 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_condition397 = new BitSet(new long[]{0x1000201000204200L});
    public static final BitSet FOLLOW_value_in_condition401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTITY_in_condition411 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_condition413 = new BitSet(new long[]{0x1000201000204200L});
    public static final BitSet FOLLOW_value_in_condition417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTITY_in_condition428 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_condition430 = new BitSet(new long[]{0x1000201000204200L});
    public static final BitSet FOLLOW_value_in_condition434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTITY_in_condition444 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_condition446 = new BitSet(new long[]{0x1000201000204200L});
    public static final BitSet FOLLOW_value_in_condition450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTITY_in_condition461 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_condition463 = new BitSet(new long[]{0x1000201000204200L});
    public static final BitSet FOLLOW_value_in_condition467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTITY_in_condition485 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_IN_in_condition487 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_list_in_condition489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANCESTOR_in_ancestorCondition509 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_IS_in_ancestorCondition511 = new BitSet(new long[]{0x1000201000204200L});
    public static final BitSet FOLLOW_value_in_ancestorCondition513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_value530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECIMAL_in_value541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_value549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOLEAN_in_value557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_value565 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_IDENTITY_in_value567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_value575 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DECIMAL_in_value579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNCTION_in_value587 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_list_in_value589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_list611 = new BitSet(new long[]{0x1000201000204200L});
    public static final BitSet FOLLOW_value_in_list615 = new BitSet(new long[]{0x0A00000000000000L});
    public static final BitSet FOLLOW_59_in_list620 = new BitSet(new long[]{0x1000201000204200L});
    public static final BitSet FOLLOW_value_in_list624 = new BitSet(new long[]{0x0A00000000000000L});
    public static final BitSet FOLLOW_57_in_list631 = new BitSet(new long[]{0x0000000000000002L});

}
