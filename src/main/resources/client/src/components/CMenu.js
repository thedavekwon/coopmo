import React, {PureComponent} from 'react';
import {getComponentFromId} from '../figmaComponents';

export class CMenu extends PureComponent {
  constructor(props){
    super(props);
    this.handleClick = this.handleClick.bind(this);
  }
  handleClick(){
    console.log('The link was clicked.');
  }

  state = {};
  render() {
    const Component = getComponentFromId(this.props.nodeId);
    return( 
      <Component onClick={this.handleClick} {...this.props} {...this.state} />
      );
  }
}
