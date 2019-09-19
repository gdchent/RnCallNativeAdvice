


import React from 'react'
import { requireNativeComponent, View } from 'react-native'
import PropTypes from 'prop-types'

const AndroidTimerView = requireNativeComponent('TimerView', NativeTimerView)
/**
 * 需求分析 ：调用原生封装好的原生控件
 */
class NativeTimerView extends React.PureComponent {

    static propTypes = {
        stimer: PropTypes.number,
    };
    
    render() {
        return (
            <AndroidTimerView
                style={{ width: 100, height: 100, backgroundColor: 'red' }}
                start={this.props.stimer}
            />
        )
    }
}
export default NativeTimerView

