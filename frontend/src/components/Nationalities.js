import React, {Component} from 'react';
import PropTypes from "prop-types";

class Nationalities extends Component{

    constructor(props) {
        super(props);
        this.state = {
            nationalities:[],
            nationality:''
        }
    }

    render() {
        return (this.props.nationalities.map((n) => {
            if(n.name !== this.props.nationality) {
                return <option value={n.name} key={n.id}>{n.name}</option>
            }
            else
            {
                return <option value={n.name} key={n.id} selected="selected">{n.name}</option>
            }
        }))
    }
}

//PropTypes
Nationalities.propTypes = {
    nationalities:PropTypes.array.isRequired
}
export default Nationalities;