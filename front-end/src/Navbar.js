import React from "react";
import PageKey from "./PageKey";
import {Link} from "react-router-dom";

class Navbar extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="container-fluid">
                    <a className="navbar-brand" href="#">spring-cloud-and-exercise</a>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <NavItem
                                pageKey={PageKey.statics.Students}
                            />
                            <NavItem
                                pageKey={PageKey.statics.GetStudent}
                            />
                            <NavItem
                                pageKey={PageKey.statics.CreateStudent}
                            />
                            <NavItem
                                pageKey={PageKey.statics.Addresses}
                            />
                        </ul>
                    </div>
                </div>
            </nav>
        )
    }
}

class NavItem extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            url: props.pageKey.url,
            text: props.pageKey.text
        }
    }

    render() {
        return (
            <li className="nav-item">
                <Link className="nav-link" aria-current="page" to={this.state.url}>{this.state.text}</Link>
            </li>
        );
    }
}

export default Navbar;