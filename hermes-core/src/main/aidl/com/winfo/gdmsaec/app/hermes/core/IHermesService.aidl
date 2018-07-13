// IHermesService.aidl
package com.winfo.gdmsaec.app.hermes.core;

import com.winfo.gdmsaec.app.hermes.core.model.Request;
import com.winfo.gdmsaec.app.hermes.core.model.Response;

interface IHermesService {

    Response send(in Request request);
}
