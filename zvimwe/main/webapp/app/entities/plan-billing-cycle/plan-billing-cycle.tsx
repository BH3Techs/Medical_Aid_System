import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlanBillingCycle } from 'app/shared/model/plan-billing-cycle.model';
import { getEntities, reset } from './plan-billing-cycle.reducer';

export const PlanBillingCycle = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const planBillingCycleList = useAppSelector(state => state.planBillingCycle.entities);
  const loading = useAppSelector(state => state.planBillingCycle.loading);
  const totalItems = useAppSelector(state => state.planBillingCycle.totalItems);
  const links = useAppSelector(state => state.planBillingCycle.links);
  const entity = useAppSelector(state => state.planBillingCycle.entity);
  const updateSuccess = useAppSelector(state => state.planBillingCycle.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="plan-billing-cycle-heading" data-cy="PlanBillingCycleHeading">
        <Translate contentKey="medicalAidSystemApp.planBillingCycle.home.title">Plan Billing Cycles</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="medicalAidSystemApp.planBillingCycle.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/plan-billing-cycle/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="medicalAidSystemApp.planBillingCycle.home.createLabel">Create new Plan Billing Cycle</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={planBillingCycleList ? planBillingCycleList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {planBillingCycleList && planBillingCycleList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="medicalAidSystemApp.planBillingCycle.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('periodUnit')}>
                    <Translate contentKey="medicalAidSystemApp.planBillingCycle.periodUnit">Period Unit</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('periodValue')}>
                    <Translate contentKey="medicalAidSystemApp.planBillingCycle.periodValue">Period Value</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('dateConfiguration')}>
                    <Translate contentKey="medicalAidSystemApp.planBillingCycle.dateConfiguration">Date Configuration</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('billingDate')}>
                    <Translate contentKey="medicalAidSystemApp.planBillingCycle.billingDate">Billing Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="medicalAidSystemApp.planBillingCycle.plans">Plans</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {planBillingCycleList.map((planBillingCycle, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/plan-billing-cycle/${planBillingCycle.id}`} color="link" size="sm">
                        {planBillingCycle.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`medicalAidSystemApp.PeriodUnit.${planBillingCycle.periodUnit}`} />
                    </td>
                    <td>{planBillingCycle.periodValue}</td>
                    <td>
                      <Translate contentKey={`medicalAidSystemApp.DateConfiguration.${planBillingCycle.dateConfiguration}`} />
                    </td>
                    <td>{planBillingCycle.billingDate}</td>
                    <td>
                      {planBillingCycle.plans ? <Link to={`/plans/${planBillingCycle.plans.id}`}>{planBillingCycle.plans.id}</Link> : ''}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/plan-billing-cycle/${planBillingCycle.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/plan-billing-cycle/${planBillingCycle.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/plan-billing-cycle/${planBillingCycle.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="medicalAidSystemApp.planBillingCycle.home.notFound">No Plan Billing Cycles found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default PlanBillingCycle;
