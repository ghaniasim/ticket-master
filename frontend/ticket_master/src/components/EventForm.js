import React, { Component } from 'react';
import { Table, Container, Input, Button, Label, Form, FormGroup } from 'reactstrap';
import { Link } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import Moment from 'react-moment';
import "react-datepicker/dist/react-datepicker.css";

import AppNav from './AppNav';

class EventForm extends Component {

    emptyItem = {
        name: '',
        event_date: new Date()
    }

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            date: new Date(),
            isLoading: true,
            events: [],
            item: this.emptyItem
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);
        this.editEvent = this.editEvent.bind(this);
    }

    async handleSubmit(event) {
        const item = this.state.item;
        await fetch(`/api/events`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        });
        event.preventDefault();
        this.props.history.push("/events");
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({ item });
    }
    
    handleDateChange(date) {
        let item = {...this.state.item};
        item.event_date = date;
        this.setState({ item });
    }

    async remove(id) {
        const response = await fetch(`/api/events/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        try {
            const body = await response.json();
        if(body.httpStatus === "BAD_REQUEST") {
            console.log(body.message);
        } else {
            let updatedEvents = [...this.state.events].filter(i => i.id !== id);
            this.setState({ events: updatedEvents });
        }
        } catch(e) {} 
        this.componentDidMount();
    }

    editEvent(id) {
        window.localStorage.setItem("eventId", id);
        this.props.history.push('/editEvent');
    }

    async componentDidMount() {
        const response = await fetch('/api/events');
        const body = await response.json();
        this.setState({events: body, isLoading: false});
    }

    render() {
        const title = <h3 style={{marginTop: '10px'}}>Event Form</h3>
        const {events, isLoading} = this.state;

        if(isLoading)
             return (<div>Loading...</div>);

        let rows = events.map(event => 
            <tr key={event.id}>
                <td>{event.name}</td>
                <td><Moment date={event.event_date} format="DD/MM/YYYY" /></td>
                <td><Button size="sm" color="secondary" onClick={() => this.editEvent(event.id)} >Modify</Button></td>
                <td><Button size="sm" color="danger" onClick={() => this.remove(event.id)}>Delete</Button></td>
            </tr>   
        )

        return (
            <div>
                <AppNav />
                <Container>
                    {title}
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="name">Title</Label>
                            <Input type="text" name="name" id="name" value={this.state.item.name}
                                onChange={this.handleChange} autoComplete="name" />
                        </FormGroup>

                        <FormGroup>
                            <Label for="event_date">Date</Label>
                            <DatePicker selected={this.state.item.event_date} onChange={this.handleDateChange} />
                        </FormGroup>
                        
                        <FormGroup>
                            <Button color="primary" type="submit">Create</Button>{' '}
                            <Button color="secondary" tag={Link} to="/admin">Cancel</Button>
                        </FormGroup>
                    </Form>
                </Container>

                {''}
                    <Container>
                        <h3>Events List</h3>
                        <Table className="mt-4">
                            <thead>
                                <tr>
                                    <th width="30%">Name</th>   
                                    <th>Date</th>
                                    <th width="10%">Action</th>
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

export default EventForm;