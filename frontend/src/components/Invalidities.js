import React, {Component} from 'react';
import PropTypes from "prop-types";

class Invalidities extends Component{

    constructor(props) {
        super(props);
        this.state = {
            invalidities:[],
            invalidity: ''
        }
    }

    render() {
        return (this.props.invalidities.map((i) => {
            if(i.name !== this.props.invalidity) {
                return <option value={i.name} key={i.id}>{i.name}</option>
            }
            else
            {
                return <option value={i.name} key={i.id} selected="selected">{i.name}</option>
            }
        }))
    }
}

//PropTypes
Invalidities.propTypes = {
    invalidities:PropTypes.array.isRequired
}
export default Invalidities;