package me.arun.vcinch.data;

/**
 * Created by Arun Pandian M on 24/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
public class ModelEmptyErrorData {
    public boolean isError;
   public boolean isProgressbar;
   public String Error;

    public ModelEmptyErrorData(boolean isError, boolean isProgressbar, String error) {
        this.isError = isError;
        this.isProgressbar = isProgressbar;
        Error = error;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public boolean isProgressbar() {
        return isProgressbar;
    }

    public void setProgressbar(boolean progressbar) {
        isProgressbar = progressbar;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}
