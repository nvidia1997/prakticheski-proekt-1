import clsx from "clsx";
import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {setMoviesViewModeAction} from "../../../movies.actions";
import {VIEW_MODES} from "../../../movies.constants";
import {selectMoviesViewMode} from "../../../movies.selectors";

export default function ViewToggle() {
    const dispatch = useDispatch();
    const viewMode = useSelector(selectMoviesViewMode);

    const onSetViewMode = (newViewMode: string) => {
        dispatch(setMoviesViewModeAction(newViewMode));
    };

    return (
        <div className="row">
            <div className="col-lg-12 my-3">
                <div className="float-right">
                    <div className="btn-group">
                        <button
                            className={clsx({
                                "btn btn-primary btn-sm": true,
                                active: viewMode === VIEW_MODES.LIST
                            })}
                            id="list"
                            onClick={() => onSetViewMode(VIEW_MODES.LIST)}
                        >
                            List View
                        </button>

                        <button
                            className={clsx({
                                "btn btn-success btn-sm": true,
                                active: viewMode === VIEW_MODES.GRID
                            })}
                            id="grid"
                            onClick={() => onSetViewMode(VIEW_MODES.GRID)}
                        >
                            Grid View
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}
