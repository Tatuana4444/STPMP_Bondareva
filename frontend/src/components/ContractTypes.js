import React, {Component} from 'react';
import PropTypes from "prop-types";

class ContractTypes extends Component{

    constructor(props) {
        super(props);
        this.state = {
            contractTypes:[],
            contractType:''
        }
    }

    render() {
        return (this.props.contractTypes.map((f) => {
            if(f.name !== this.props.contractType) {
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
ContractTypes.propTypes = {
    contractTypes:PropTypes.array.isRequired,
    contractType:PropTypes.object.isRequired
}
export default ContractTypes;