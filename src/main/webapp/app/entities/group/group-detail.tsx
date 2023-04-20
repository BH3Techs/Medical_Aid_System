import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './group.reducer';

export const GroupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const groupEntity = useAppSelector(state => state.group.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="groupDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.group.detail.title">Group</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{groupEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.group.name">Name</Translate>
            </span>
          </dt>
          <dd>{groupEntity.name}</dd>
          <dt>
            <span id="groupType">
              <Translate contentKey="medicalAidSystemApp.group.groupType">Group Type</Translate>
            </span>
          </dt>
          <dd>{groupEntity.groupType}</dd>
          <dt>
            <span id="dateRegistered">
              <Translate contentKey="medicalAidSystemApp.group.dateRegistered">Date Registered</Translate>
            </span>
          </dt>
          <dd>
            {groupEntity.dateRegistered ? (
              <TextFormat value={groupEntity.dateRegistered} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.group.contactDetails">Contact Details</Translate>
          </dt>
          <dd>{groupEntity.contactDetails ? groupEntity.contactDetails.id : ''}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.group.bankingDetails">Banking Details</Translate>
          </dt>
          <dd>{groupEntity.bankingDetails ? groupEntity.bankingDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/group/${groupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GroupDetail;
