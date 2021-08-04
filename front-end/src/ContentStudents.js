import React from "react";
import PageKey from "./PageKey";
import Services from "./Services";
import {Link} from "react-router-dom";

class ContentStudents extends React.Component {
    constructor(props) {
        super(props);
        const queryParams = new URLSearchParams(window.location.search);
        const page = queryParams.get("page");
        this.state = {
            students: [],
            totalPages: 1,
            currentPage: page === null ? 1 :
                page <= 0 ? 1 : page
        }
        this.pageChanged = this.pageChanged.bind(this)
    }

    componentDidMount() {
        fetch(Services.StudentService + "?page=" + (this.state.currentPage - 1))
            .then(res => res.json())
            .then((data) => {
                this.setState({
                    students: data.students,
                    totalItems: data.totalItems,
                    totalPages: data.totalPages
                })
            })
    }

    pageChanged(event) {
        const clickedPage = event.currentTarget.value

        const url = PageKey.statics.Students.url
        this.props.history.push(`${url}?page=${clickedPage}`)

        fetch(Services.StudentService + "?page=" + (clickedPage - 1))
            .then(res => res.json())
            .then((data) => {
                this.setState({
                    students: data.students,
                    totalItems: data.totalItems,
                    totalPages: data.totalPages
                })
            })

        if (clickedPage !== this.state.currentPage) {
            this.setState({
                currentPage: clickedPage
            })
        }
    }

    render() {
        return (
            <div className="card">
                <div className="card-header">
                    {PageKey.statics.Students.text}
                </div>
                <div className="card-body">
                    <table className="table">
                        <thead>
                        <tr>
                            <th style={{width: 30 + '%'}} scope="col">First Name</th>
                            <th style={{width: 30 + '%'}} scope="col">Last Name</th>
                            <th style={{width: 30 + '%'}} scope="col">Email</th>
                            <th style={{width: 10 + '%'}} scope="col">Details</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.students.map((s, i) =>
                            <tr key={s.email}>
                                <td>
                                    {s.firstName}
                                </td>
                                <td>
                                    {s.lastName}
                                </td>
                                <td>
                                    {s.email}
                                </td>
                                <td>
                                    <Link to={PageKey.statics.GetStudent.url + '?email=' + s.email}>
                                        <i className="bi-card-text"></i>
                                    </Link>
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                    <div className="d-flex justify-content-center">
                        <nav aria-label="Page navigation example">
                            <ul className="pagination">
                                <PaginationArrow
                                    isPrevious={true}
                                    isDisabled={this.state.currentPage <= 1}
                                    onClickCallback={this.pageChanged}
                                    changeToPageNumber={Number(this.state.currentPage) - 1}
                                />
                                <PaginationNumbers
                                    totalPages={this.state.totalPages}
                                    currentPage={this.state.currentPage}
                                    onClickCallback={this.pageChanged}
                                />
                                <PaginationArrow
                                    isPrevious={false}
                                    isDisabled={this.state.currentPage >= this.state.totalPages}
                                    onClickCallback={this.pageChanged}
                                    changeToPageNumber={Number(this.state.currentPage) + 1}
                                />
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        );
    }
};

class PaginationArrow extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            arrow: this.props.isPrevious ? '<<' : '>>',
            disabled: this.props.isDisabled ? 'disabled' : '',
            onClickCallback: this.props.onClickCallback,
            changeToPageNumber: this.props.changeToPageNumber
        }
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        return {
            disabled: nextProps.isDisabled ? 'disabled' : '',
            changeToPageNumber: nextProps.changeToPageNumber
        };
    }

    render() {
        return (
            <li className={`page-item ${this.state.disabled}`}>
                <button className="page-link" value={this.state.changeToPageNumber} onClick={this.state.onClickCallback}>
                    <span aria-hidden="true">{this.state.arrow}</span>
                </button>
            </li>
        )
    }
}

class PaginationNumbers extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            totalPages: this.props.totalPages,
            currentPage: this.props.currentPage,
            onClickCallback: this.props.onClickCallback
        }
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        return {
            totalPages: nextProps.totalPages,
            currentPage: nextProps.currentPage
        };
    }

    render() {
        return (
            Array.from(Array(this.state.totalPages), (e, i) => {
                return (
                    <li key={i}
                        className={`page-item ${this.state.currentPage - 1 === i ? 'active' : ''}`}>
                        <button className="page-link" value={i + 1} onClick={this.state.onClickCallback}>
                            {i + 1}
                        </button>
                    </li>
                )
            })
        );
    }
}

export default ContentStudents;