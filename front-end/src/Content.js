import React from "react";
import {Route, Switch} from "react-router-dom";
import ContentGetStudent from "./ContentGetStudent";
import ContentStudents from "./ContentStudents";
import ContentCreateStudent from "./ContentCreateStudent";
import ContentAddresses from "./ContentAddresses";

class Content extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        return (
            <main>
                <div className="row mt-5">
                    <div className="col-1"></div>
                    <div className="col-10">
                        <Switch>
                            <Route path="/" component={ContentStudents} exact/>
                            <Route path="/student" component={ContentGetStudent}/>
                            <Route path="/student/create" component={ContentCreateStudent}/>
                            <Route path="/addresses" component={ContentAddresses}/>
                            <Route component={Error}/>
                        </Switch>
                    </div>
                    <div className="col-1"></div>
                </div>
            </main>
        )
    }
};

export default Content;