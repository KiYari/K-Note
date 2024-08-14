import {FC} from "react";
import SiderProps from "@/layout/sider/sider.props";
import styles from "./sider.module.css"
import BasicComponent from "@/component/basicComponent/BasicComponent";
import { Menu } from 'antd';
import type { MenuProps } from 'antd';

type MenuItem = Required<MenuProps>['items'][number];

const items: MenuItem[] = [{
    key: 'sub1',
    label: 'Example 1',
    },
    {
        key: 'sub2',
        label: 'Example 2',
    },
    {
        key: 'sub3',
        label: 'Example 3',
    },
]

const Sider:FC<SiderProps> = ({ style  , headerHeight, isSiderOpened, siderWidth,  ...props }) => {

    const combinedStyle = {
        width: siderWidth,
        left: isSiderOpened ? 0 : -siderWidth + '%',
        ...style
    }

    return(
        <BasicComponent className={styles.main} style={combinedStyle} {...props}>
            <Menu style={{top: headerHeight}} items={items}/>
        </BasicComponent>
    )
}

export default Sider;