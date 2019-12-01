import React, { Component } from 'react';
import { Table, Container, Button } from 'reactstrap';
import { Link } from 'react-router-dom';

import AppNav from './AppNav';

class Admin extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            bookings: []
        }
    }

    async componentDidMount() {
        const response = await fetch('/api/bookings');
        const body = await response.json();
        this.setState({bookings: body, isLoading: false});
    }

    render() {
        const {isLoading} = this.state;

        if(isLoading)
             return (<div>Loading...</div>);

        let rows = this.state.bookings.map((booking, i) => 
            <tr>
                <td>{this.state.bookings[i][0]}</td>
                <td>{this.state.bookings[i][1]}</td>
                <td>{this.state.bookings[i][2]}</td>
                <td>{this.state.bookings[i][3]}</td>
                <td>{this.state.bookings[i][4]}</td>
            </tr>   
        )

        return (
            <div>
                <AppNav />
                <Button color="primary" tag={Link} to="/event" style={{margin: '10px', float: 'right'}}>Create an Event</Button>
                {''}
                    <Container>
                        <h3 style={{marginTop: '20px'}}>Booking List</h3>
                        <Table className="mt-4">
                            <thead>
                                <tr>
                                    <th width="20%">First Name</th>
                                    <th width="20%">Last Name</th>
                                    <th width="10%">Age</th>
                                    <th width="30%">Email</th>
                                    <th width="20%">Event</th>   
                                </tr>
                            </thead>

                            <tbody>
                                {rows}
                            </tbody>
                        </Table>
                    </Container>
            </div>
        );
    }
}

export default Admin;