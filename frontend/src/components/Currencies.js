import React, {Component} from 'react';
import PropTypes from "prop-types";

class Currencies extends Component{

    constructor(props) {
        super(props);
        this.state = {
            currencies:[],
            currency:''
        }
    }

    render() {
        return (this.props.currencies.map((f) => {
            if(f.name !== this.props.currency) {
                return <option value={f.name} key={f.id}>{f.name}</option>
            }
            else
            {
                return <option value={f.name} key={f.id} selected="selected">{f.name}</option>
            }
        }))
    }
}

//PropTypes
Currencies.propTypes = {
    currencies:PropTypes.array.isRequired,
    currency:PropTypes.object.isRequired
}
export default Currencies;