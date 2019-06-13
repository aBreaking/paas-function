package com.sitech.esb.mouse.filter;

public class FilterFactory {

    public static Filter getFilter(String filterName){
        if(filterName==null){
            return new DefaultFilter();
        }
        if (filterName.equals(PqFilter.FILTER_NAME)){
            return new PqFilter();
        }

        return new DefaultFilter();
    }
}
