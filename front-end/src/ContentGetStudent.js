import React from "react";
import PageKey from "./PageKey";
import Services from "./Services";

class ContentGetStudent extends React.Component {
    constructor(props) {
        super(props);
        const queryParams = new URLSearchParams(window.location.search);
        const email = queryParams.get("email");
        this.state = {
            email: email === null ? '' : email,
            student: null,
            notFound: false
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        if (this.state.email !== '') {
            this.fetchStudent(this.state.email)
        }
    }

    handleChange(event) {
        const email = event.currentTarget.value
        const url = PageKey.statics.GetStudent.url
        if (email === '') {
            this.props.history.push(url)
        } else {
            this.props.history.push(`${url}?email=${event.currentTarget.value}`)
        }
        this.setState({
            email: event.currentTarget.value
        })
    }

    handleSubmit(event) {
        event.preventDefault();

        this.fetchStudent(this.state.email)
    }

    fetchStudent(email) {
        fetch(Services.StudentService + "/" + email)
            .then(response => {
                    if (response.ok) {
                        return response.json()
                    } else if (response.status === 404) {
                        return Promise.reject('error 404')
                    } else {
                        return Promise.reject('some other error: ' + response.status)
                    }
                }
            )
            .then((data) => {
                this.setState({
                    student: data,
                    notFound: false
                })
            })
            .catch((error) => {
                console.log(error)
                this.setState({
                    student: null,
                    notFound: error === 'error 404'
                })
            })
    }

    render() {
        let studentDetailsDiv
        const student = this.state.student
        if (student !== null) {
            studentDetailsDiv = (
                <div className="student-details mt-3">
                    <div className="student-details-header mb-3">
                        <h3>{student.firstName} {student.lastName}</h3>
                        <b>Email: </b><span>{student.email}</span>
                    </div>
                    <div className="student-details-body">
                        <h4>Address</h4>
                        <b>Street: </b><span>{student.address.street}</span><br/>
                        <b>Number: </b><span>{student.address.number}</span><br/>
                        <b>Number extra: </b><span>{student.address.numberExtra}</span><br/>
                        <b>City: </b><span>{student.address.city}</span><br/>
                        <b>Postal code: </b><span>{student.address.postalCode}</span><br/>
                    </div>
                </div>
            )
        }

        let notFoundMessage
        if (this.state.notFound) {
            notFoundMessage = (
                <div className="student-details mt-3">
                    <div className="student-details-header mb-3">
                        <h3>Student not found.</h3>
                    </div>
                </div>
            )
        }

        return (
            <div className="card">
                <div className="card-header">
                    {PageKey.statics.GetStudent.text}
                </div>
                <div className="card-body">
                    <form onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="inputEmail">Email address</label>
                            <input type="email" className="form-control" id="inputEmail"
                                   aria-describedby="emailHelp" placeholder="Enter email"
                                   value={this.state.email} onChange={this.handleChange}/>
                            <small id="emailHelp" className="form-text text-muted">Email will never be shared.</small>
                        </div>
                        <button type="submit" className="btn btn-primary mt-2">Submit</button>
                    </form>

                    {studentDetailsDiv}
                    {notFoundMessage}
                </div>
            </div>
        );
    }
};

export default ContentGetStudent;