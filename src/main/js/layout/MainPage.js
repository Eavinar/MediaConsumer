import React, { Component } from 'react';
import MediaResults from './MediaResults';
import LoadingSpinner from './LoadingSpinner';
import axios from 'axios';

class MainPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            term: "",
            media: [],
            loading: false
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleClick = (e) => {
        e.preventDefault();

        this.setState({
            loading: true
        })

        const response = axios.get(
            'http://localhost:8080/getMedia',
            {
                params: {
                    'term': this.state.term
                }
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*',
                }
            }
        ).then((response) => {
            this.setState({
                media: response.data,
            });
        }).catch((error) => {
            if (error.response) {
                alert("Something went wrong. Please try again later.");
            }
        }).finally(() => {
            this.setState({
                loading: false
            })
        });
    }

    handleChange({ target }) {
        this.setState({
            [target.name]: target.value
        })
    }

    render() {
        return (
            <div id="main-content" className="container-fluid" >
                <div className="jumbotron text-center">
                    <form className="navbar-form navbar-left" role="search">
                        <div className="input-group">
                            <input type="text" className="form-control" placeholder="Search For Media..." name="term" value={this.state.term} onChange={this.handleChange} />
                            <span className="input-group-btn">
                                <button type="submit" className="btn btn-primary"
                                        onClick={this.handleClick}>Search</button>
                            </span>
                        </div>
                        {this.state.loading ? <LoadingSpinner /> : <MediaResults media={this.state.media} />}
                    </form>
                </div>
            </div >
        );
    }
}

export default MainPage;
