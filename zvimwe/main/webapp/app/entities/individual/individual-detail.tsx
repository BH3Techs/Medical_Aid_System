import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './individual.reducer';

export const IndividualDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const individualEntity = useAppSelector(state => state.individual.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="individualDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.individual.detail.title">Individual</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{individualEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="medicalAidSystemApp.individual.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{individualEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="medicalAidSystemApp.individual.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{individualEntity.lastName}</dd>
          <dt>
            <span id="initial">
              <Translate contentKey="medicalAidSystemApp.individual.initial">Initial</Translate>
            </span>
          </dt>
          <dd>{individualEntity.initial}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="medicalAidSystemApp.individual.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {individualEntity.dateOfBirth ? (
              <TextFormat value={individualEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gender">
              <Translate contentKey="medicalAidSystemApp.individual.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{individualEntity.gender}</dd>
          <dt>
            <span id="nationalId">
              <Translate contentKey="medicalAidSystemApp.individual.nationalId">National Id</Translate>
            </span>
          </dt>
          <dd>{individualEntity.nationalId}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.individual.contactDetails">Contact Details</Translate>
          </dt>
          <dd>{individualEntity.contactDetails ? individualEntity.contactDetails.id : ''}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.individual.bankingDetails">Banking Details</Translate>
          </dt>
          <dd>{individualEntity.bankingDetails ? individualEntity.bankingDetails.id : ''}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.individual.riskProfile">Risk Profile</Translate>
          </dt>
          <dd>{individualEntity.riskProfile ? individualEntity.riskProfile.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/individual" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/individual/${individualEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IndividualDetail;
