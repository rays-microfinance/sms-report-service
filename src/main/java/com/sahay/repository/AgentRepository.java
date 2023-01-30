package com.sahay.repository;

import com.sahay.entity.Agent;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    @Query(value = "SELECT [Name], [PhoneNumber] AS phoneNumber ,[FloatAccount] AS agentCode ,[region_name] AS region,[village], [district_name] AS district,[zone_name] AS zone,[commissionAccount] " +
            "FROM [mobile_banking].[dbo].[Agent] agent\n" +
            "INNER JOIN regions reg ON reg.region_code = agent.region_code\n" +
            "JOIN districts dis ON dis.district_code = agent.district_code\n" +
            "INNER JOIN zones zone ON zone.zone_code = agent.zone_code\n" +
            "WHERE FloatAccount = ?1", nativeQuery = true)
    Agent getAgentByCode(String code);
}
