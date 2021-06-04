import {Box, Button} from "@material-ui/core";
import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import ResponsiveTable from "../../../components/responsive-table/responsive-table";
import {createColumn} from "../../../components/responsive-table/responsive-table.utils";
import {ROUTE_PATHS} from "../../index";
import * as moviesSelectors from "../../movies/movies.selectors";
import * as moviesActions from "../../movies/movies.actions";
import * as YearsService from "../service";

function YearsList() {
    const dispatch = useDispatch();
    const years = useSelector(moviesSelectors.selectYears) || [];

    useEffect(() => {
        dispatch(moviesActions.loadYearsAction());
    }, []);

    const _onDeleteClick = (id: number) => {
        YearsService.deleteById(id)
            .then(() => {
                dispatch(moviesActions.loadYearsAction());
            });
    };

    return (
        <Box>
            <Box mb={2}>
                <Link
                    className="btn btn-link"
                    to={ROUTE_PATHS.YEARS_CREATE}
                >
                    Create new
                </Link>
            </Box>

            <ResponsiveTable
                tableId="YearsTable"
                noItemsNode="No items found..."
                columns={[
                    createColumn({
                        xs: true,
                        name: "id"
                    }),
                    createColumn({
                        xs: true,
                        name: "value"
                    }),
                    createColumn({
                        xs: true,
                        name: "edit"
                    }),
                    createColumn({
                        xs: true,
                        name: "delete"
                    })
                ]}
                rows={years.map(
                    (item) => ([
                        item.id,
                        item.value,
                        <Link
                            className="btn btn-primary"
                            to={ROUTE_PATHS.YEARS_EDIT(item.id.toString())}
                        >
                            Edit
                        </Link>,
                        <Button
                            size="small"
                            variant="contained"
                            color="secondary"
                            onClick={() => _onDeleteClick(item.id)}
                        >
                            Delete
                        </Button>
                    ]),
                )}
            />
        </Box>
    );
}

export default YearsList;
