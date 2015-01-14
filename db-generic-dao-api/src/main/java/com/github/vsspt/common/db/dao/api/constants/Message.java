package com.github.vsspt.common.db.dao.api.constants;

public enum Message {
    
    DELETE_INSTANCE("Deleting [{}] instance."),
    DELETE_INSTANCE_SUCCESS("Delete successful [{}] with ID [{}]."),
    DELETE_INSTANCE_ERROR("Delete failed [{}] with ID [{}]. Error [{}]."),
    EXECUTE_QUERY("Getting all [{}] instances."),
    EXECUTE_QUERY_SUCCESS("Getting all [{}] instances. Found [{}] elements."),
    EXECUTE_QUERY_ERROR("ExecuteQuery [{}] failed [{}]. Error [{}]."),
    FIND_ALL("Finding ALL [{}] instances."),
    FIND_ALL_SUCCESS("Find ALL [{}] successful, result size [{}]."),
    FIND_ALL_PAGED_SUCCESS("Find ALL [{}] successful, result size [{}], total records [{}]."),	
    FIND_BY_EXAMPLE("Finding [{}] instance by example."),
    FIND_BY_EXAMPLE_SUCCESS("Find by example [{}] successful, result size [{}]."),
    FIND_BY_EXAMPLE_ERROR("Find By Example [{}] failed. Error [{}]."),
    NULL_OBJECT("Object is null."),
    FIND_BY_ID("Looking for [{}] with ID [{}]."),
    FIND_BY_ID_SUCCESS_FOUND("Found [{}] with ID [{}]."),
    FIND_BY_ID_SUCCESS_NOT_FOUND("[{}] with ID [{}] not found."),
    FIND_BY_ID_ERROR("Find failed for [{}] with ID [{}]. Error [{}]."),
    UPDATE_INSTANCE("Updating [{}] instance."),
    UPDATE_INSTANCE_SUCCESS("Update successful."),
    MERGE_INSTANCE_ERROR("Merge failed [{}]. Error [{}]."),
    REGISTER_ACTION("Register Action on Table [{}]."),
    REGISTER_ACTION_SUCCESS("Register Action [{}], User [{}], on Table [{}]."),
    REGISTER_ACTION_ERROR("Cannot record audit on [{}], action [{}]."),
    SAVE_INSTANCE("Saving [{}] instance."),
    SAVE_INSTANCE_SUCCESS("Saved [{}] with ID [{}]."),
    SAVE_INSTANCE_ERROR("Error Saving [{}], Error [{}]."),
    FIND("Finding [{}] instances."),
    FIND_SUCCESS("Finding [{}] instances successful, result size [{}]."),
    MERGE_INSTANCE("Merging [{}] instance."),
    MERGE_INSTANCE_SUCCESS("Merge successful."),
    SAVE_OR_UPDATE_INSTANCE("Saving Or Updating [{}] instance."),
    SAVE_OR_UPDATE_INSTANCE_SUCCESS("Saving Or Updating successful."),    
    
    ;
    
    
    private String desc;

    private Message(final String desc) {
    	this.desc = desc;
    }

    public String getMsg() {
    	return desc;
    }

    @Override
    public String toString() {
    	return desc;
    }        

}
