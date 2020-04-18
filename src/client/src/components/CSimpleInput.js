import React, { PureComponent } from 'react';

export default class CSimpleInput extends PureComponent {
  constructor(props){
    super(props);
  }

  render() {
    return(
      <div className="master" style={{backgroundColor: "rgba(0, 0, 0, 0)"}}>
         <div>
          <div style={{"zIndex":1}} className="outerDiv centerer">
            <div
              id="I35:280;35:276"
              style={{"marginLeft":32,"marginRight":32,"flexGrow":1,"marginTop":48,"marginBottom":24,"backgroundColor":"rgba(0, 0, 0, 0)"}}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I35:280;35:277"
                    style={{"width":"100%","marginLeft":"0%","height":"100%","top":"0%","border":"1px solid rgba(38, 38, 38, 1)","borderRadius":"8px 8px 8px 8px"}}
                    className="innerDiv"
                  >
                    <div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{"zIndex":2}} className="outerDiv centerer">
            <div
              id="I35:280;35:278"
              style={{"marginLeft":32,"marginRight":32,"flexGrow":1,"marginTop":20,"marginBottom":77,"color":"rgba(38, 38, 38, 1)","fontSize":18,"fontWeight":700,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
              className="innerDiv"
            >
              <div>
                <span style={{}} key="end">{this.props.name}</span>
              </div>
            </div>
          </div>
        </div>
        <div style={{"zIndex":3}} className="outerDiv centerer">
            <div
              id="I35:280;35:275"
              style={{"marginLeft":48,"marginRight":44,"flexGrow":1,"marginTop":58,"marginBottom":34,"color":"rgba(38, 38, 38, 1)","fontSize":16,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"181.43999099731445%","letterSpacing":"0px"}}
              className="innerDiv"
            >
              <div>
                <span style={{}} key="end">
                  
                  <input type="text" id={this.props.name} name={this.props.name} style={{"width":"100%"}}></input>
                  
                </span>
                
                {/*<span style={{}} key="end">Placeholder</span>*/}
              </div>
            </div>
          </div>
      </div>
    );
  }
}
