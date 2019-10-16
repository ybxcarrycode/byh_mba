package com.xszj.mba.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "QUESTION_BEAN".
*/
public class QuestionBeanDao extends AbstractDao<QuestionBean, Long> {

    public static final String TABLENAME = "QUESTION_BEAN";

    /**
     * Properties of entity QuestionBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property QType = new Property(0, int.class, "qType", false, "QTYPE");
        public final static Property QId = new Property(1, long.class, "qId", true, "_id");
        public final static Property Querstion = new Property(2, String.class, "querstion", false, "QUESTION");
        public final static Property AId = new Property(3, int.class, "aId", false, "AID");
        public final static Property AStuId = new Property(4, int.class, "aStuId", false, "ASTUAID");
        public final static Property ACurId = new Property(5, int.class, "aCurId", false, "ACURID");
        public final static Property OfferAnswer = new Property(6, String.class, "offerAnswer", false, "OFFERANSWER");
    }


    public QuestionBeanDao(DaoConfig config) {
        super(config);
    }
    
    public QuestionBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"QUESTION_BEAN\" (" + //
                "\"QTYPE\" INTEGER NOT NULL ," + // 0: qType
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 1: qId
                "\"QUESTION\" TEXT," + // 2: querstion
                "\"AID\" INTEGER NOT NULL ," + // 3: aId
                "\"ASTUAID\" INTEGER NOT NULL ," + // 4: aStuId
                "\"ACURID\" INTEGER NOT NULL ," + // 5: aCurId
                "\"OFFERANSWER\" TEXT);"); // 6: offerAnswer
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"QUESTION_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, QuestionBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getQType());
        stmt.bindLong(2, entity.getQId());
 
        String querstion = entity.getQuerstion();
        if (querstion != null) {
            stmt.bindString(3, querstion);
        }
        stmt.bindLong(4, entity.getAId());
        stmt.bindLong(5, entity.getAStuId());
        stmt.bindLong(6, entity.getACurId());
 
        String offerAnswer = entity.getOfferAnswer();
        if (offerAnswer != null) {
            stmt.bindString(7, offerAnswer);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, QuestionBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getQType());
        stmt.bindLong(2, entity.getQId());
 
        String querstion = entity.getQuerstion();
        if (querstion != null) {
            stmt.bindString(3, querstion);
        }
        stmt.bindLong(4, entity.getAId());
        stmt.bindLong(5, entity.getAStuId());
        stmt.bindLong(6, entity.getACurId());
 
        String offerAnswer = entity.getOfferAnswer();
        if (offerAnswer != null) {
            stmt.bindString(7, offerAnswer);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 1);
    }    

    @Override
    public QuestionBean readEntity(Cursor cursor, int offset) {
        QuestionBean entity = new QuestionBean( //
            cursor.getInt(offset + 0), // qType
            cursor.getLong(offset + 1), // qId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // querstion
            cursor.getInt(offset + 3), // aId
            cursor.getInt(offset + 4), // aStuId
            cursor.getInt(offset + 5), // aCurId
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // offerAnswer
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, QuestionBean entity, int offset) {
        entity.setQType(cursor.getInt(offset + 0));
        entity.setQId(cursor.getLong(offset + 1));
        entity.setQuerstion(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAId(cursor.getInt(offset + 3));
        entity.setAStuId(cursor.getInt(offset + 4));
        entity.setACurId(cursor.getInt(offset + 5));
        entity.setOfferAnswer(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(QuestionBean entity, long rowId) {
        entity.setQId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(QuestionBean entity) {
        if(entity != null) {
            return entity.getQId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(QuestionBean entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}