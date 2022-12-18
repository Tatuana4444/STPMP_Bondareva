import React, {Component} from 'react';
import PropTypes from "prop-types";
import axios from "axios";

class Cities extends Component{

    constructor(props) {
        super(props);
    }

    render() {
        return (this.props.cities.map((c) => {
            if(c.name !== this.props.city) {
                return <option value={c.name} key={c.id}>{c.name}</option>
            }
            else
            {
                return <option value={c.name} key={c.id} selected="selected">{c.name}</option>
            }
        }))
    }
}

//PropTypes
Cities.propTypes = {
    cities:PropTypes.array.isRequired,
    city:PropTypes.object.isRequired
}
export default Cities;