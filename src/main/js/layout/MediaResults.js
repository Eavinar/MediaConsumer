import React, { Component } from 'react'
import Results from './Results'

class MediaResults extends Component {

    render() {
        return (
            <div className="table-responsive">
                <table className="table table-hover table-striped">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Media</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.props.media.map(
                        (data, i) => (
                            <Results
                                key={i}
                                id={i}
                                title={data.title}
                                authors={data.authors}
                                type={data.type}
                            />
                        ))}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default MediaResults;