package com.thepan.reservationapiserver.domain.member.entity

import com.thepan.reservationapiserver.domain.base.BaseEntity
import com.thepan.reservationapiserver.domain.notice.entity.Notice
import jakarta.persistence.*

@Entity
@NamedEntityGraph(
    name = "Member.roles",
    attributeNodes = [NamedAttributeNode(value = "roles", subgraph = "Member.roles.role")],
    subgraphs = [NamedSubgraph(name = "Member.roles.role", attributeNodes = [NamedAttributeNode("role")])]
)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    var id: Long? = null,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false, length = 30, unique = true)
    var email: String,
    @Column(nullable = false, length = 11)
    var phoneNumber: String,
    var password: String,
    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var roles: MutableSet<MemberRole> = mutableSetOf(),
    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var noticeList: MutableList<Notice> = ArrayList()
) : BaseEntity() {
    constructor(
        name: String,
        email: String,
        phoneNumber: String,
        password: String,
        roles: List<Role>
    ) : this(
        name = name,
        email = email,
        phoneNumber = phoneNumber,
        password = password
    ) {
        this.roles = roles.map { r -> MemberRole(this, r) }.toMutableSet()
    }
}