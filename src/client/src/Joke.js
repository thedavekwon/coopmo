import React from 'react';
import {Fade, CardText, Card, CardBody, CardTitle, Button} from 'reactstrap';
import './App.css';

class Joke extends React.Component{
    constructor(props) {
        super(props);
        this.state = { fadeIn: false};
        this.toggle = this.toggle.bind(this);
    }
    render(){
        let { setup, ans} = this.props.humor;
        return(
            <div>
                <Card>
                    <CardBody>
                        <CardTitle>{setup}</CardTitle>
                        <Button color="primary" onClick={this.toggle}>Button</Button>
                        <Fade in={this.state.fadeIn} className='my-2'>
                        <CardText>{ans}</CardText>
                        </Fade>
                    </CardBody>
                </Card>
            </div>
        )
    }
    

    toggle(){
        this.setState({
          fadeIn: !this.state.fadeIn
        });
    }
};
export default Joke;