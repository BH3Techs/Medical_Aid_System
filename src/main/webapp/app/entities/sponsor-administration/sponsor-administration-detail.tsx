import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sponsor-administration.reducer';

export const SponsorAdministrationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sponsorAdministrationEntity = useAppSelector(state => state.sponsorAdministration.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sponsorAdministrationDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.sponsorAdministration.detail.title">SponsorAdministration</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sponsorAdministrationEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="medicalAidSystemApp.sponsorAdministration.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{sponsorAdministrationEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="medicalAidSystemApp.sponsorAdministration.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{sponsorAdministrationEntity.lastName}</dd>
          <dt>
            <span id="initial">
              <Translate contentKey="medicalAidSystemApp.sponsorAdministration.initial">Initial</Translate>
            </span>
          </dt>
          <dd>{sponsorAdministrationEntity.initial}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="medicalAidSystemApp.sponsorAdministration.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {sponsorAdministrationEntity.dateOfBirth ? (
              <TextFormat value={sponsorAdministrationEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="sponsorId">
              <Translate contentKey="medicalAidSystemApp.sponsorAdministration.sponsorId">Sponsor Id</Translate>
            </span>
          </dt>
          <dd>{sponsorAdministrationEntity.sponsorId}</dd>
          <dt>
            <span id="sponsorType">
              <Translate contentKey="medicalAidSystemApp.sponsorAdministration.sponsorType">Sponsor Type</Translate>
            </span>
          </dt>
          <dd>{sponsorAdministrationEntity.sponsorType}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.sponsorAdministration.contactDetails">Contact Details</Translate>
          </dt>
          <dd>{sponsorAdministrationEntity.contactDetails ? sponsorAdministrationEntity.contactDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sponsor-administration" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sponsor-administration/${sponsorAdministrationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SponsorAdministrationDetail;
