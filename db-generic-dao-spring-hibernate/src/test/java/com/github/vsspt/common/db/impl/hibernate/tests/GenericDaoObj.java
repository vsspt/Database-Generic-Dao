package com.github.vsspt.common.db.impl.hibernate.tests;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class GenericDaoObj implements Serializable{

    private Long ID;
    private String aString;
    private Date aDate;
    private int aInt;

    public Date getaDate() {
	return aDate;
    }

    public int getaInt() {
	return aInt;
    }

    public String getaString() {
	return aString;
    }

    public Long getID() {
	return ID;
    }

    public void setaDate(final Date aDate) {
	this.aDate = aDate;
    }

    public void setaInt(final int aInt) {
	this.aInt = aInt;
    }

    public void setaString(final String aString) {
	this.aString = aString;
    }

    public void setID(final Long iD) {
	ID = iD;
    }
    
    @Override
    public String toString()
    {
	return "ID = [" + ID + "]; aString = [" + aString + "]; aDate = [" + aDate + "]; aInt = [" + aInt + "]";
    }

}
