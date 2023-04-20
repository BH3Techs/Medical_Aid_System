import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './next-of-kin.reducer';

export const NextOfKinDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nextOfKinEntity = useAppSelector(state => state.nextOfKin.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nextOfKinDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.nextOfKin.detail.title">NextOfKin</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{nextOfKinEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.nextOfKin.name">Name</Translate>
            </span>
          </dt>
          <dd>{nextOfKinEntity.name}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="medicalAidSystemApp.nextOfKin.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{nextOfKinEntity.identifier}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.nextOfKin.contactDetails">Contact Details</Translate>
          </dt>
          <dd>{nextOfKinEntity.contactDetails ? nextOfKinEntity.contactDetails.id : ''}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.nextOfKin.policy">Policy</Translate>
          </dt>
          <dd>{nextOfKinEntity.policy ? nextOfKinEntity.policy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/next-of-kin" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/next-of-kin/${nextOfKinEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NextOfKinDetail;
