import React, {PureComponent} from 'react';
import {CFeed} from './components/CFeed';
import {FriendsList} from './components/FriendsList';
import {MenuButton} from './components/MenuButton';
import {MainPage} from './components/MainPage';

export class MasterMainPage extends PureComponent {
    render() {
        return <div className="master" style={{backgroundColor: "rgba(255, 255, 255, 1)"}}>
            <CMainPage {...this.props} nodeId="1:2"/>
        </div>
    }
}

class CMainPage1D2 extends PureComponent {
    render() {
      return (
        <div>
          <div style={{}} className="outerDiv">
            <div
              id="100:9"
              style={{"marginLeft":115,"width":795,"minWidth":795,"height":null,"marginTop":215,"marginBottom":-6,"minHeight":815,"backgroundColor":"rgba(0, 0, 0, 0)","overflow":"hidden"}}
              className="innerDiv"
            >
              <CFeed {...this.props} nodeId="100:9" />
            </div>
          </div>
          <div style={{"zIndex":1}} className="outerDiv centerer">
            <div
              id="99:218"
              style={{"width":"20.416666666666668%","marginLeft":"72.91666666666667%","height":"71.2890625%","top":"20.99609375%","backgroundColor":"rgba(0, 0, 0, 0)","overflow":"hidden"}}
              className="innerDiv"
            >
              <CFriendsList {...this.props} nodeId="99:218" />
            </div>
          </div>
          <div style={{"zIndex":2}} className="outerDiv centerer">
            <div
              id="100:0"
              style={{"width":"100%","marginLeft":"0%","height":"10.3515625%","top":"0%","backgroundColor":"rgba(0, 0, 0, 0)"}}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I100:0;4:3"
                    style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                    className="innerDiv"
                  >
                    <div>
                    </div>
                  </div>
                </div>
                <div style={{"zIndex":1}} className="outerDiv centerer">
                  <div
                    id="I100:0;30:174"
                    style={{"marginLeft":28,"marginRight":1264,"flexGrow":1,"marginTop":31.927703857421875,"marginBottom":22.987953186035156,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                    className="innerDiv"
                  >
                    <CMenuButton {...this.props} nodeId="I100:0;30:174" />
                  </div>
                </div>
                <div style={{"zIndex":2}} className="outerDiv centerer">
                  <div
                    id="I100:0;97:225"
                    style={{"marginLeft":269,"marginRight":293,"flexGrow":1,"marginTop":0,"marginBottom":0,"color":"rgba(0, 0, 0, 1)","fontSize":73,"fontWeight":700,"fontFamily":"Muli","textAlign":"CENTER","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{"fontSize":73,"fontStyle":"normal","lineHeight":"NaN%","letterSpacing":"undefinedpx"}} key="4">Coop</span>
                      <span style={{"fontSize":73,"fontStyle":"normal","lineHeight":"NaN%","letterSpacing":"undefinedpx"}} key="end">mo</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      );
    }
  }
  
class CFeed38D1057 extends PureComponent {
render() {
    return (
    <div>
        <div style={{}} className="outerDiv centerer">
        <div
            id="I38:1057;30:46"
            style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":760,"backgroundColor":"rgba(0, 0, 0, 0)"}}
            className="innerDiv"
        >
            <div>
            <div style={{}} className="outerDiv centerer">
                <div
                id="I38:1057;30:46;30:9"
                style={{"marginLeft":530,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(0, 0, 0, 0)"}}
                className="innerDiv"
                >
                <div>
                    <div style={{}} className="outerDiv centerer">
                    <div
                        id="I38:1057;30:46;30:9;24:1"
                        style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":14,"marginBottom":14,"color":"rgba(0, 0, 0, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"CENTER","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                        className="innerDiv"
                    >
                        <div>
                        <span style={{}} key="end">Public</span>
                        </div>
                    </div>
                    </div>
                    <div style={{"zIndex":1}} className="outerDiv centerer">
                    <div
                        id="I38:1057;30:46;30:9;30:5"
                        style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":53,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                        className="innerDiv"
                    >
                        <div>
                        </div>
                    </div>
                    </div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":1}} className="outerDiv centerer">
                <div
                id="I38:1057;30:46;30:6"
                style={{"marginLeft":265,"marginRight":265,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                className="innerDiv"
                >
                <div>
                    <div style={{}} className="outerDiv centerer">
                    <div
                        id="I38:1057;30:46;30:6;67:837"
                        style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)","borderRadius":"10px 10px 0px 0px"}}
                        className="innerDiv"
                    >
                        <div>
                        </div>
                    </div>
                    </div>
                    <div style={{"zIndex":1}} className="outerDiv centerer">
                    <div
                        id="I38:1057;30:46;30:6;30:3"
                        style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":16,"marginBottom":17,"color":"rgba(255, 255, 255, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"CENTER","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                        className="innerDiv"
                    >
                        <div>
                        <span style={{}} key="end">Friends</span>
                        </div>
                    </div>
                    </div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":2}} className="outerDiv centerer">
                <div
                id="I38:1057;30:46;30:12"
                style={{"marginLeft":0,"marginRight":530,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(0, 0, 0, 0)"}}
                className="innerDiv"
                >
                <div>
                    <div style={{}} className="outerDiv centerer">
                    <div
                        id="I38:1057;30:46;30:12;24:1"
                        style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":14,"marginBottom":14,"color":"rgba(0, 0, 0, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"CENTER","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                        className="innerDiv"
                    >
                        <div>
                        <span style={{}} key="end">Me</span>
                        </div>
                    </div>
                    </div>
                    <div style={{"zIndex":1}} className="outerDiv centerer">
                    <div
                        id="I38:1057;30:46;30:12;30:5"
                        style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":53,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                        className="innerDiv"
                    >
                        <div>
                        </div>
                    </div>
                    </div>
                </div>
                </div>
            </div>
            </div>
        </div>
        </div>
        <div style={{"zIndex":1}} className="outerDiv centerer">
        <div
            id="I38:1057;30:55"
            style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":55,"marginBottom":608,"backgroundColor":"rgba(0, 0, 0, 0)"}}
            className="innerDiv"
        >
            <div>
            <div style={{}} className="outerDiv centerer">
                <div
                id="I38:1057;30:55;30:25"
                style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(255, 255, 255, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":1}} className="outerDiv centerer">
                <div
                id="I38:1057;30:55;30:27"
                style={{"marginLeft":15,"marginRight":588.0000305175781,"flexGrow":1,"marginTop":17,"marginBottom":105.61333274841309,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Date Time</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":2}} className="outerDiv centerer">
                <div
                id="I38:1057;30:55;30:28"
                style={{"marginLeft":321,"marginRight":15,"flexGrow":1,"marginTop":17,"marginBottom":18,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"RIGHT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Message</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":3}} className="outerDiv centerer">
                <div
                id="I38:1057;30:55;30:29"
                style={{"marginLeft":15,"marginRight":552,"flexGrow":1,"marginTop":61.8133544921875,"marginBottom":64.85330963134766,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 1 paid </span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":4}} className="outerDiv centerer">
                <div
                id="I38:1057;30:55;30:30"
                style={{"marginLeft":15,"marginRight":605,"flexGrow":1,"marginTop":103,"marginBottom":29.746673583984375,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 2</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":5}} className="outerDiv centerer">
                <div
                id="I38:1057;30:55;38:1352"
                style={{"marginLeft":73,"marginRight":72,"flexGrow":1,"marginTop":151,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
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
            id="I38:1057;30:61"
            style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":207,"marginBottom":456,"backgroundColor":"rgba(0, 0, 0, 0)"}}
            className="innerDiv"
        >
            <div>
            <div style={{}} className="outerDiv centerer">
                <div
                id="I38:1057;30:61;30:25"
                style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(255, 255, 255, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":1}} className="outerDiv centerer">
                <div
                id="I38:1057;30:61;30:27"
                style={{"marginLeft":15,"marginRight":588.0000305175781,"flexGrow":1,"marginTop":17,"marginBottom":105.61333274841309,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Date Time</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":2}} className="outerDiv centerer">
                <div
                id="I38:1057;30:61;30:28"
                style={{"marginLeft":321,"marginRight":15,"flexGrow":1,"marginTop":17,"marginBottom":18,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"RIGHT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Message</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":3}} className="outerDiv centerer">
                <div
                id="I38:1057;30:61;30:29"
                style={{"marginLeft":15,"marginRight":552,"flexGrow":1,"marginTop":61.8133544921875,"marginBottom":64.85330963134766,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 1 paid </span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":4}} className="outerDiv centerer">
                <div
                id="I38:1057;30:61;30:30"
                style={{"marginLeft":15,"marginRight":605,"flexGrow":1,"marginTop":103,"marginBottom":29.746673583984375,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 2</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":5}} className="outerDiv centerer">
                <div
                id="I38:1057;30:61;38:1352"
                style={{"marginLeft":73,"marginRight":72,"flexGrow":1,"marginTop":151,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            </div>
        </div>
        </div>
        <div style={{"zIndex":3}} className="outerDiv centerer">
        <div
            id="I38:1057;30:67"
            style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":359,"marginBottom":304,"backgroundColor":"rgba(0, 0, 0, 0)"}}
            className="innerDiv"
        >
            <div>
            <div style={{}} className="outerDiv centerer">
                <div
                id="I38:1057;30:67;30:25"
                style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(255, 255, 255, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":1}} className="outerDiv centerer">
                <div
                id="I38:1057;30:67;30:27"
                style={{"marginLeft":15,"marginRight":588.0000305175781,"flexGrow":1,"marginTop":17,"marginBottom":105.61333274841309,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Date Time</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":2}} className="outerDiv centerer">
                <div
                id="I38:1057;30:67;30:28"
                style={{"marginLeft":321,"marginRight":15,"flexGrow":1,"marginTop":17,"marginBottom":18,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"RIGHT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Message</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":3}} className="outerDiv centerer">
                <div
                id="I38:1057;30:67;30:29"
                style={{"marginLeft":15,"marginRight":552,"flexGrow":1,"marginTop":61.8133544921875,"marginBottom":64.85330963134766,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 1 paid </span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":4}} className="outerDiv centerer">
                <div
                id="I38:1057;30:67;30:30"
                style={{"marginLeft":15,"marginRight":605,"flexGrow":1,"marginTop":103,"marginBottom":29.746673583984375,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 2</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":5}} className="outerDiv centerer">
                <div
                id="I38:1057;30:67;38:1352"
                style={{"marginLeft":73,"marginRight":72,"flexGrow":1,"marginTop":151,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            </div>
        </div>
        </div>
        <div style={{"zIndex":4}} className="outerDiv centerer">
        <div
            id="I38:1057;30:73"
            style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":511,"marginBottom":152,"backgroundColor":"rgba(0, 0, 0, 0)"}}
            className="innerDiv"
        >
            <div>
            <div style={{}} className="outerDiv centerer">
                <div
                id="I38:1057;30:73;30:25"
                style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(255, 255, 255, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":1}} className="outerDiv centerer">
                <div
                id="I38:1057;30:73;30:27"
                style={{"marginLeft":15,"marginRight":588.0000305175781,"flexGrow":1,"marginTop":17,"marginBottom":105.61333274841309,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Date Time</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":2}} className="outerDiv centerer">
                <div
                id="I38:1057;30:73;30:28"
                style={{"marginLeft":321,"marginRight":15,"flexGrow":1,"marginTop":17,"marginBottom":18,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"RIGHT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Message</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":3}} className="outerDiv centerer">
                <div
                id="I38:1057;30:73;30:29"
                style={{"marginLeft":15,"marginRight":552,"flexGrow":1,"marginTop":61.8133544921875,"marginBottom":64.85330963134766,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 1 paid </span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":4}} className="outerDiv centerer">
                <div
                id="I38:1057;30:73;30:30"
                style={{"marginLeft":15,"marginRight":605,"flexGrow":1,"marginTop":103,"marginBottom":29.746673583984375,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 2</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":5}} className="outerDiv centerer">
                <div
                id="I38:1057;30:73;38:1352"
                style={{"marginLeft":73,"marginRight":72,"flexGrow":1,"marginTop":151,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            </div>
        </div>
        </div>
        <div style={{"zIndex":5}} className="outerDiv centerer">
        <div
            id="I38:1057;30:79"
            style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":663,"marginBottom":0,"backgroundColor":"rgba(0, 0, 0, 0)"}}
            className="innerDiv"
        >
            <div>
            <div style={{}} className="outerDiv centerer">
                <div
                id="I38:1057;30:79;30:25"
                style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(255, 255, 255, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":1}} className="outerDiv centerer">
                <div
                id="I38:1057;30:79;30:27"
                style={{"marginLeft":15,"marginRight":588.0000305175781,"flexGrow":1,"marginTop":17,"marginBottom":105.61333274841309,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Date Time</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":2}} className="outerDiv centerer">
                <div
                id="I38:1057;30:79;30:28"
                style={{"marginLeft":321,"marginRight":15,"flexGrow":1,"marginTop":17,"marginBottom":18,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"RIGHT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Message</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":3}} className="outerDiv centerer">
                <div
                id="I38:1057;30:79;30:29"
                style={{"marginLeft":15,"marginRight":552,"flexGrow":1,"marginTop":61.8133544921875,"marginBottom":64.85330963134766,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 1 paid </span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":4}} className="outerDiv centerer">
                <div
                id="I38:1057;30:79;30:30"
                style={{"marginLeft":15,"marginRight":605,"flexGrow":1,"marginTop":103,"marginBottom":29.746673583984375,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 2</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":5}} className="outerDiv centerer">
                <div
                id="I38:1057;30:79;38:1352"
                style={{"marginLeft":73,"marginRight":72,"flexGrow":1,"marginTop":151,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            </div>
        </div>
        </div>
        <div style={{"zIndex":6}} className="outerDiv centerer">
        <div
            id="I38:1057;30:85"
            style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":815,"marginBottom":-152,"backgroundColor":"rgba(0, 0, 0, 0)"}}
            className="innerDiv"
        >
            <div>
            <div style={{}} className="outerDiv centerer">
                <div
                id="I38:1057;30:85;30:25"
                style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(255, 255, 255, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":1}} className="outerDiv centerer">
                <div
                id="I38:1057;30:85;30:27"
                style={{"marginLeft":15,"marginRight":588.0000305175781,"flexGrow":1,"marginTop":17,"marginBottom":105.61333274841309,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Date Time</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":2}} className="outerDiv centerer">
                <div
                id="I38:1057;30:85;30:28"
                style={{"marginLeft":321,"marginRight":15,"flexGrow":1,"marginTop":17,"marginBottom":18,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"RIGHT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Message</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":3}} className="outerDiv centerer">
                <div
                id="I38:1057;30:85;30:29"
                style={{"marginLeft":15,"marginRight":552,"flexGrow":1,"marginTop":61.8133544921875,"marginBottom":64.85330963134766,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 1 paid </span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":4}} className="outerDiv centerer">
                <div
                id="I38:1057;30:85;30:30"
                style={{"marginLeft":15,"marginRight":605,"flexGrow":1,"marginTop":103,"marginBottom":29.746673583984375,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 2</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":5}} className="outerDiv centerer">
                <div
                id="I38:1057;30:85;38:1352"
                style={{"marginLeft":73,"marginRight":72,"flexGrow":1,"marginTop":151,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            </div>
        </div>
        </div>
        <div style={{"zIndex":7}} className="outerDiv centerer">
        <div
            id="I38:1057;30:91"
            style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":967,"marginBottom":-304,"backgroundColor":"rgba(0, 0, 0, 0)"}}
            className="innerDiv"
        >
            <div>
            <div style={{}} className="outerDiv centerer">
                <div
                id="I38:1057;30:91;30:25"
                style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(255, 255, 255, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":1}} className="outerDiv centerer">
                <div
                id="I38:1057;30:91;30:27"
                style={{"marginLeft":15,"marginRight":588.0000305175781,"flexGrow":1,"marginTop":17,"marginBottom":105.61333274841309,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Date Time</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":2}} className="outerDiv centerer">
                <div
                id="I38:1057;30:91;30:28"
                style={{"marginLeft":321,"marginRight":15,"flexGrow":1,"marginTop":17,"marginBottom":18,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"RIGHT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Message</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":3}} className="outerDiv centerer">
                <div
                id="I38:1057;30:91;30:29"
                style={{"marginLeft":15,"marginRight":552,"flexGrow":1,"marginTop":61.8133544921875,"marginBottom":64.85330963134766,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 1 paid </span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":4}} className="outerDiv centerer">
                <div
                id="I38:1057;30:91;30:30"
                style={{"marginLeft":15,"marginRight":605,"flexGrow":1,"marginTop":103,"marginBottom":29.746673583984375,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 2</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":5}} className="outerDiv centerer">
                <div
                id="I38:1057;30:91;38:1352"
                style={{"marginLeft":73,"marginRight":72,"flexGrow":1,"marginTop":151,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            </div>
        </div>
        </div>
        <div style={{"zIndex":8}} className="outerDiv centerer">
        <div
            id="I38:1057;30:97"
            style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":1119,"marginBottom":-456,"backgroundColor":"rgba(0, 0, 0, 0)"}}
            className="innerDiv"
        >
            <div>
            <div style={{}} className="outerDiv centerer">
                <div
                id="I38:1057;30:97;30:25"
                style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(255, 255, 255, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":1}} className="outerDiv centerer">
                <div
                id="I38:1057;30:97;30:27"
                style={{"marginLeft":15,"marginRight":588.0000305175781,"flexGrow":1,"marginTop":17,"marginBottom":105.61333274841309,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Date Time</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":2}} className="outerDiv centerer">
                <div
                id="I38:1057;30:97;30:28"
                style={{"marginLeft":321,"marginRight":15,"flexGrow":1,"marginTop":17,"marginBottom":18,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"RIGHT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Message</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":3}} className="outerDiv centerer">
                <div
                id="I38:1057;30:97;30:29"
                style={{"marginLeft":15,"marginRight":552,"flexGrow":1,"marginTop":61.8133544921875,"marginBottom":64.85330963134766,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 1 paid </span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":4}} className="outerDiv centerer">
                <div
                id="I38:1057;30:97;30:30"
                style={{"marginLeft":15,"marginRight":605,"flexGrow":1,"marginTop":103,"marginBottom":29.746673583984375,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 2</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":5}} className="outerDiv centerer">
                <div
                id="I38:1057;30:97;38:1352"
                style={{"marginLeft":73,"marginRight":72,"flexGrow":1,"marginTop":151,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            </div>
        </div>
        </div>
        <div style={{"zIndex":9}} className="outerDiv centerer">
        <div
            id="I38:1057;30:103"
            style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":1271,"marginBottom":-608,"backgroundColor":"rgba(0, 0, 0, 0)"}}
            className="innerDiv"
        >
            <div>
            <div style={{}} className="outerDiv centerer">
                <div
                id="I38:1057;30:103;30:25"
                style={{"marginLeft":0,"marginRight":0,"flexGrow":1,"marginTop":0,"marginBottom":0,"backgroundColor":"rgba(255, 255, 255, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            <div style={{"zIndex":1}} className="outerDiv centerer">
                <div
                id="I38:1057;30:103;30:27"
                style={{"marginLeft":15,"marginRight":588.0000305175781,"flexGrow":1,"marginTop":17,"marginBottom":105.61333274841309,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Date Time</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":2}} className="outerDiv centerer">
                <div
                id="I38:1057;30:103;30:28"
                style={{"marginLeft":321,"marginRight":15,"flexGrow":1,"marginTop":17,"marginBottom":18,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"RIGHT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Message</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":3}} className="outerDiv centerer">
                <div
                id="I38:1057;30:103;30:29"
                style={{"marginLeft":15,"marginRight":552,"flexGrow":1,"marginTop":61.8133544921875,"marginBottom":64.85330963134766,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 1 paid </span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":4}} className="outerDiv centerer">
                <div
                id="I38:1057;30:103;30:30"
                style={{"marginLeft":15,"marginRight":605,"flexGrow":1,"marginTop":103,"marginBottom":29.746673583984375,"color":"rgba(102, 0, 153, 1)","fontSize":24,"fontWeight":400,"fontFamily":"Muli","textAlign":"LEFT","fontStyle":"normal","lineHeight":"125%","letterSpacing":"0px"}}
                className="innerDiv"
                >
                <div>
                    <span style={{}} key="end">Name 2</span>
                </div>
                </div>
            </div>
            <div style={{"zIndex":5}} className="outerDiv centerer">
                <div
                id="I38:1057;30:103;38:1352"
                style={{"marginLeft":73,"marginRight":72,"flexGrow":1,"marginTop":151,"marginBottom":0,"backgroundColor":"rgba(102, 0, 153, 1)"}}
                className="innerDiv"
                >
                <div>
                </div>
                </div>
            </div>
            </div>
        </div>
        </div>
    </div>
    );
}
}