package com.company.samplesales.app;

import io.jmix.oidc.claimsmapper.BaseClaimsRolesMapper;
import io.jmix.security.role.ResourceRoleRepository;
import io.jmix.security.role.RowLevelRoleRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("sales_AppClaimsRolesMapper")
public class AppClaimsRolesMapper extends BaseClaimsRolesMapper {

    public AppClaimsRolesMapper(ResourceRoleRepository resourceRoleRepository,
                                RowLevelRoleRepository rowLevelRoleRepository) {
        super(resourceRoleRepository, rowLevelRoleRepository);
    }

    @Override
    protected Collection<String> getResourceRolesCodes(Map<String, Object> claims) {
        return getCodesFrom(claims, "jmix_resource");
    }

    @Override
    protected Collection<String> getRowLevelRoleCodes(Map<String, Object> claims) {
        return getCodesFrom(claims, "jmix_row_level");
    }

    @SuppressWarnings("unchecked")
    protected Collection<String> getCodesFrom(Map<String, Object> claims, String rolePrefix) {
        Object claimRoles = claims.get("kk_roles");
        if (claimRoles instanceof Collection<?>) {
            Collection<String> roles = (Collection<String>) claimRoles;
            List<String> resultRoles = new ArrayList<>();
            for (String role : roles) {
                if (!role.startsWith(rolePrefix)) {
                    continue;
                }
                String code = role.split("\\$")[1];
                resultRoles.add(code);
            }
            return resultRoles;
        } else {
            return Collections.emptySet();
        }
    }
}