import React, {Component} from "react";
import IconButton from "@material-ui/core/IconButton";
import {ArrowDropDown, Description, FolderOpen} from "@material-ui/icons";
import Popover from "@material-ui/core/Popover";
import Paper from "@material-ui/core/Paper";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import TreeView from '@material-ui/lab/TreeView';
import TreeItem from '@material-ui/lab/TreeItem';
import Typography from "@material-ui/core/Typography";
import FormLabel from "@material-ui/core/FormLabel";
import {ROOT} from "../../constants/constants";
import {withStyles} from "@material-ui/core";

class TreeObjectChooser extends Component {
    constructor(props) {
        super(props);
        this.state = {
            anchorEl: null
        };
    }

    selectObject = (node) => {
        this.setState({
            anchorEl: null
        }, () => {
            this.props.selectObject(node);
        });
    };

    openTree = (event) => {
        if (this.props.enabled) {
            this.setState({
                anchorEl: event.target
            });
        }
    };

    closeTree = () => {
        if (this.props.enabled) {
            this.setState({
                anchorEl: null
            });
        }
    };

    renderTree = (nodes) => {
        const {classes} = this.props;
        return (
            <TreeItem key={nodes.genreId} nodeId={nodes.genreId}
                      label={
                          <div className={classes.labelRoot}>
                              {Array.isArray(nodes.childGenres) && nodes.childGenres.length > 0 ?
                                  <FolderOpen className={classes.labelIcon}/> :
                                  <Description className={classes.labelIcon}/>}
                              <Typography className={classes.labelText} title={nodes.name}>
                                  {nodes.name}
                              </Typography>
                          </div>
                      }
                      onLabelClick={(event) => {
                          if (nodes.genreId !== ROOT.toString()) {
                              event.preventDefault();
                              this.selectObject(nodes);
                          }
                      }}
            >
                {Array.isArray(nodes.childGenres) ? nodes.childGenres.map((node) => this.renderTree(node)) : null}
            </TreeItem>
        );
    };

    render() {
        const {classes, data, currentObject, enabled, rootName} = this.props;
        const {anchorEl} = this.state;
        const treeData = {
            genreId: ROOT.toString(),
            name: rootName,
            childGenres: data
        };
        const inputStyle = {
            padding: '0 16px',
            height: '100%',
            border: '',
            flex: 1,
            lineHeight: '28px',
            fontSize: 16,
            color: 'black',
            paddingTop: 6,
            paddingBottom: 6,
            textOverflow: ' ellipsis',
            whiteSpace: 'nowrap',
            overflow: 'hidden'
        };
        const open = Boolean(anchorEl);
        return (
            <>
                <Paper component="form"
                       className={classes.root}
                       style={{
                           borderColor: enabled ? ' #979797' : 'rgba(0, 0, 0, 0.26)'
                       }}>
                    <FormLabel onClick={this.openTree} title={currentObject ? currentObject.name : ''}
                               style={inputStyle}>{currentObject ? currentObject.name : ''}</FormLabel>
                    <IconButton
                        disableFocusRipple={true}
                        disabled={!enabled}
                        disableRipple={true}
                        size={'small'}
                        onClick={open ? this.closeTree : this.openTree}
                    >
                        <ArrowDropDown/>
                    </IconButton>
                </Paper>
                <Popover
                    anchorEl={anchorEl}
                    onClose={this.closeTree}
                    open={open}
                    className={classes.popOver}
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'left',
                    }}
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'left',
                    }}>
                    <TreeView
                        className={classes.treeRoot}
                        defaultCollapseIcon={<ExpandMoreIcon/>}
                        defaultExpandIcon={<ChevronRightIcon/>}
                    >
                        {this.renderTree(treeData)}
                    </TreeView>
                </Popover>
            </>
        );
    }
}

const styles = theme => ({
    root: {
        padding: 0,
        border: '1px solid #979797',
        boxSizing: 'border-box',
        borderRadius: 4,
        display: 'flex',
        alignItems: 'center',
        boxShadow: 'none'
    },
    treeRoot: {
        height: 300,
        flexGrow: 1,
        width: 300,
        padding: 16,
        overflow: 'auto'
    },
    popOver: {
        maxHeight: '',
        maxWidth: ''
    },
    labelRoot: {
        display: 'flex',
        alignItems: 'center',
        padding: theme.spacing(0.5, 0),
        color: 'rgba(0, 0, 0, 0.87)'
    },
    labelIcon: {
        marginRight: theme.spacing(1),
        color: '#3949AB'
    },
    labelText: {
        fontWeight: 'inherit',
        flexGrow: 1,
        textOverflow: ' ellipsis',
        whiteSpace: 'nowrap',
        color: 'rgba(0, 0, 0, 0.87)',
        overflow: 'hidden',
        minWidth: '250px'
    }
});

export default withStyles(styles)(TreeObjectChooser);