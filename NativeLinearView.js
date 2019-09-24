import React from 'react'
import { requireNativeComponent, View,Dimensions  } from 'react-native'
import PropTypes from 'prop-types'

export const { width, height } = Dimensions.get('window')
const AndroidBannerContainer= requireNativeComponent('BannerContainer', NativeLinearView)
class NativeLinearView extends React.PureComponent{

    static propTypes = {
            ...View.propTypes
    }

    render(){
        const {style }=this.props
        return (
            <AndroidBannerContainer
                style={style}

            />
        )
    }
}

export default NativeLinearView