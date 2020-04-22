import React from 'react';
import {Col, Container, Jumbotron, Row} from 'reactstrap';
import './App.css';
import Joke from './Joke';


class App extends React.Component{
  
  constructor() {
    super();
    this.state = {
      jokes: [
        {
          setup: "setup1",
          ans: "ans1"
        },
        {
          setup: "setup2",
          ans: "ans2"
        },
        {
          setup: "setup3",
          ans: "ans3"
        },
        {
          setup: "setup4",
          ans: "ans4"
        }
      ]
    }
  }
  render() {
    let jokeCards = this.state.jokes.map(humor =>{
      return (
        <Col className="p-3" sm="4">
          <Joke humor={humor}/>
        </Col>
      )
    })
    return(
      <div>
        <Jumbotron>
          <h1 className="display-3">Hello World!</h1>
          <p className="lead">This is a simple hero unit, a simple Jumbotron-style component</p>
          <p>It usess stuff</p>
          <p className="lead"></p>
        </Jumbotron>
        <Container fluid>
          <Row>
            {jokeCards}
          </Row>
        </Container>
      </div>
    )
  }
  

  
}

export default App;