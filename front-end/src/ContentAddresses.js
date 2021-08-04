import React from "react";
import PageKey from "./PageKey";

class ContentAddresses extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="card">
                <div className="card-header">
                    {PageKey.statics.Addresses.text}
                </div>
                <div className="card-body">
                    Content
                </div>
            </div>
        );
    }
};

export default ContentAddresses;