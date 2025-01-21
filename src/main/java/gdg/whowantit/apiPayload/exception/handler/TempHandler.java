package gdg.whowantit.apiPayload.exception.handler;

import gdg.whowantit.apiPayload.code.BaseErrorCode;
import gdg.whowantit.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {
    public TempHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
