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

import { IBenefitLimit } from 'app/shared/model/benefit-limit.model';
import { getEntities, reset } from './benefit-limit.reducer';

export const BenefitLimit = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const benefitLimitList = useAppSelector(state => state.benefitLimit.entities);
  const loading = useAppSelector(state => state.benefitLimit.loading);
  const totalItems = useAppSelector(state => state.benefitLimit.totalItems);
  const links = useAppSelector(state => state.benefitLimit.links);
  const entity = useAppSelector(state => state.benefitLimit.entity);
  const updateSuccess = useAppSelector(state => state.benefitLimit.updateSuccess);

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
      <h2 id="benefit-limit-heading" data-cy="BenefitLimitHeading">
        <Translate contentKey="medicalAidSystemApp.benefitLimit.home.title">Benefit Limits</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="medicalAidSystemApp.benefitLimit.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/benefit-limit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="medicalAidSystemApp.benefitLimit.home.createLabel">Create new Benefit Limit</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={benefitLimitList ? benefitLimitList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {benefitLimitList && benefitLimitList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="medicalAidSystemApp.benefitLimit.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('limitValue')}>
                    <Translate contentKey="medicalAidSystemApp.benefitLimit.limitValue">Limit Value</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('limitPeriodUnit')}>
                    <Translate contentKey="medicalAidSystemApp.benefitLimit.limitPeriodUnit">Limit Period Unit</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('limitPeriodValue')}>
                    <Translate contentKey="medicalAidSystemApp.benefitLimit.limitPeriodValue">Limit Period Value</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('active')}>
                    <Translate contentKey="medicalAidSystemApp.benefitLimit.active">Active</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="medicalAidSystemApp.benefitLimit.benefitLimitType">Benefit Limit Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="medicalAidSystemApp.benefitLimit.planBenefit">Plan Benefit</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {benefitLimitList.map((benefitLimit, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/benefit-limit/${benefitLimit.id}`} color="link" size="sm">
                        {benefitLimit.id}
                      </Button>
                    </td>
                    <td>{benefitLimit.limitValue}</td>
                    <td>
                      <Translate contentKey={`medicalAidSystemApp.PeriodUnit.${benefitLimit.limitPeriodUnit}`} />
                    </td>
                    <td>{benefitLimit.limitPeriodValue}</td>
                    <td>{benefitLimit.active ? 'true' : 'false'}</td>
                    <td>
                      {benefitLimit.benefitLimitType ? (
                        <Link to={`/benefit-limit-type/${benefitLimit.benefitLimitType.id}`}>{benefitLimit.benefitLimitType.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {benefitLimit.planBenefit ? (
                        <Link to={`/plan-benefit/${benefitLimit.planBenefit.id}`}>{benefitLimit.planBenefit.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/benefit-limit/${benefitLimit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/benefit-limit/${benefitLimit.id}/edit`}
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
                          to={`/benefit-limit/${benefitLimit.id}/delete`}
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
                <Translate contentKey="medicalAidSystemApp.benefitLimit.home.notFound">No Benefit Limits found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default BenefitLimit;
