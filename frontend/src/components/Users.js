import React, {Component} from 'react';
import PropTypes from "prop-types";

class Users extends Component{

    constructor(props) {
        super(props);
        this.state = {
            users:[],
            user:''
        }
    }

    render() {
        return (this.props.users.map((f) => {
            let s = f.lastname +' ' + f.firstname + ' ' + f.surname
            if(s !== this.props.user) {
                return <option value={s} key={f.id}>{s}</option>
            }
            else
            {
                return <option value={s} key={f.id} selected="selected">{s}</option>
            }
        }))
    }
}

//PropTypes
Users.propTypes = {
    users:PropTypes.array.isRequired,
    user:PropTypes.object.isRequired
}
export default Users;