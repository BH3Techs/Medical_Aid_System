import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISponsorAdministration } from 'app/shared/model/sponsor-administration.model';
import { getEntities, reset } from './sponsor-administration.reducer';

export const SponsorAdministration = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const sponsorAdministrationList = useAppSelector(state => state.sponsorAdministration.entities);
  const loading = useAppSelector(state => state.sponsorAdministration.loading);
  const totalItems = useAppSelector(state => state.sponsorAdministration.totalItems);
  const links = useAppSelector(state => state.sponsorAdministration.links);
  const entity = useAppSelector(state => state.sponsorAdministration.entity);
  const updateSuccess = useAppSelector(state => state.sponsorAdministration.updateSuccess);

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
      <h2 id="sponsor-administration-heading" data-cy="SponsorAdministrationHeading">
        <Translate contentKey="medicalAidSystemApp.sponsorAdministration.home.title">Sponsor Administrations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="medicalAidSystemApp.sponsorAdministration.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/sponsor-administration/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="medicalAidSystemApp.sponsorAdministration.home.createLabel">Create new Sponsor Administration</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={sponsorAdministrationList ? sponsorAdministrationList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {sponsorAdministrationList && sponsorAdministrationList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="medicalAidSystemApp.sponsorAdministration.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('firstName')}>
                    <Translate contentKey="medicalAidSystemApp.sponsorAdministration.firstName">First Name</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('lastName')}>
                    <Translate contentKey="medicalAidSystemApp.sponsorAdministration.lastName">Last Name</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('initial')}>
                    <Translate contentKey="medicalAidSystemApp.sponsorAdministration.initial">Initial</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('dateOfBirth')}>
                    <Translate contentKey="medicalAidSystemApp.sponsorAdministration.dateOfBirth">Date Of Birth</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sponsorId')}>
                    <Translate contentKey="medicalAidSystemApp.sponsorAdministration.sponsorId">Sponsor Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sponsorType')}>
                    <Translate contentKey="medicalAidSystemApp.sponsorAdministration.sponsorType">Sponsor Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="medicalAidSystemApp.sponsorAdministration.contactDetails">Contact Details</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {sponsorAdministrationList.map((sponsorAdministration, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/sponsor-administration/${sponsorAdministration.id}`} color="link" size="sm">
                        {sponsorAdministration.id}
                      </Button>
                    </td>
                    <td>{sponsorAdministration.firstName}</td>
                    <td>{sponsorAdministration.lastName}</td>
                    <td>{sponsorAdministration.initial}</td>
                    <td>
                      {sponsorAdministration.dateOfBirth ? (
                        <TextFormat type="date" value={sponsorAdministration.dateOfBirth} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{sponsorAdministration.sponsorId}</td>
                    <td>
                      <Translate contentKey={`medicalAidSystemApp.SponsorType.${sponsorAdministration.sponsorType}`} />
                    </td>
                    <td>
                      {sponsorAdministration.contactDetails ? (
                        <Link to={`/contact-details/${sponsorAdministration.contactDetails.id}`}>
                          {sponsorAdministration.contactDetails.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/sponsor-administration/${sponsorAdministration.id}`}
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
                          to={`/sponsor-administration/${sponsorAdministration.id}/edit`}
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
                          to={`/sponsor-administration/${sponsorAdministration.id}/delete`}
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
                <Translate contentKey="medicalAidSystemApp.sponsorAdministration.home.notFound">No Sponsor Administrations found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default SponsorAdministration;
