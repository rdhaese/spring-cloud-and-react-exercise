import './custom.scss';
import 'bootstrap-icons/font/bootstrap-icons.css';
import Navbar from "./Navbar";
import Content from "./Content";
import React from "react";

class App extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <Navbar />
                <Content />
            </div>
        );
    }
}

export default App;
