
package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class Regular implements Serializable {
    private String comeIn;
    private String workIn;

    public Regular() {
    }

    public Regular(String comeIn, String workIn) {
        this.comeIn = comeIn;
        this.workIn = workIn;
    }

    public String getComeIn() {
        return comeIn;
    }

    public String getWorkIn() {
        return workIn;
    }

    public void setComeIn(String comeIn) {
        this.comeIn = comeIn;
    }

    public void setWorkIn(String workIn) {
        this.workIn = workIn;
    }

    @Override
    public String toString() {
        return "Regular{" + "comeIn=" + comeIn + ", workIn=" + workIn + '}';
    }


}
