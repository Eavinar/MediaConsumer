import React, { Component } from 'react'

class Results extends Component {
    render() {
        return (
            <tr>
                <th>{ this.props.id + 1 }</th>
                <th>{ this.props.title }</th>
                <th>{ this.props.authors }</th>
                <th>{ this.props.type }</th>
            </tr>
        )
    }
}

export default Results;